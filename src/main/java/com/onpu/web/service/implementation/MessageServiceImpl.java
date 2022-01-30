package com.onpu.web.service.implementation;

import com.onpu.web.api.dto.EventType;
import com.onpu.web.api.dto.MetaDto;
import com.onpu.web.api.dto.ObjectType;
import com.onpu.web.api.util.WsSender;
import com.onpu.web.api.views.Views;
import com.onpu.web.service.interfaces.MessageService;
import com.onpu.web.store.entity.MessageEntity;
import com.onpu.web.store.entity.UserEntity;
import com.onpu.web.store.entity.UserSubscriptionEntity;
import com.onpu.web.store.repository.MessageRepository;
import com.onpu.web.store.repository.UserSubscriptionRepository;
import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Service
public class MessageServiceImpl implements MessageService {

    static final String URL_PATTERN = "https?:\\/\\/?[\\w\\d\\._\\-%\\/\\?=&#]+";
    static final String IMAGE_PATTERN = "\\.(jpeg|jpg|gif|png)$";

    static Pattern URL_REGEX = Pattern.compile(URL_PATTERN, Pattern.CASE_INSENSITIVE);
    static Pattern IMG_REGEX = Pattern.compile(IMAGE_PATTERN, Pattern.CASE_INSENSITIVE);

    MessageRepository messageRepository;

    BiConsumer<EventType, MessageEntity> wsSender;

    UserSubscriptionRepository userSubscriptionRepository;

    public MessageServiceImpl(MessageRepository messageRepository, WsSender wsSender, UserSubscriptionRepository userSubscriptionRepository) {
        this.messageRepository = messageRepository;
        this.wsSender = wsSender.getSender(ObjectType.MESSAGE, Views.FullMessage.class);
        this.userSubscriptionRepository = userSubscriptionRepository;
    }



    @Override
    public List<MessageEntity> findForUser(UserEntity userEntity) {
        List<UserEntity> channels = userSubscriptionRepository.findBySubscriber(userEntity)
                .stream()
                .filter(UserSubscriptionEntity::isActive)
                .map(UserSubscriptionEntity::getChannel)
                .collect(Collectors.toList());

        channels.add(userEntity);

        return messageRepository.findByAuthorIn(channels);
    }

    @Override
    public MessageEntity updateMessage(MessageEntity messageFromDB, MessageEntity message) {

        BeanUtils.copyProperties(message, messageFromDB, "id", "comments","author");

        fillMeta(messageFromDB);
        messageFromDB.setCreationDate(LocalDateTime.now());

        messageRepository.saveAndFlush(messageFromDB);
        wsSender.accept(EventType.UPDATE, messageFromDB);

        return messageFromDB;
    }

    @Override
    public void deleteMessage(MessageEntity message) {

        messageRepository.delete(message);
        wsSender.accept(EventType.REMOVE, message);
    }

    @Override
    public MessageEntity createMessage(MessageEntity message, UserEntity user)  {

        message.setAuthor(user);
        message.setCreationDate(LocalDateTime.now());
        fillMeta(message);

        MessageEntity createdMessage = messageRepository.saveAndFlush(message);
        wsSender.accept(EventType.CREATE, createdMessage);

        return createdMessage;
    }

    @Override
    public List<MessageEntity> getListMessages(Optional<String> optionalPrefixName) {
        optionalPrefixName = optionalPrefixName.filter(prefixName -> !prefixName.trim().isEmpty());
        List<MessageEntity> messages = optionalPrefixName
                .map(messageRepository::findAllByTextContainingIgnoreCase)
                .orElseGet(() -> messageRepository.findAll());

        return messages;
    }

    @SneakyThrows
    private void fillMeta(MessageEntity message) {
        String text = message.getText();
        Matcher matcher = URL_REGEX.matcher(text);

        if (matcher.find()) {
            String url = text.substring(matcher.start(), matcher.end());

            matcher = IMG_REGEX.matcher(url);

            message.setLink(url);

            if (matcher.find()) {
                message.setLinkCover(url);
            } else if (!url.contains("youtu")) {
                MetaDto meta = getMeta(url);

                message.setLinkCover(meta.getCover());
                message.setLinkTitle(meta.getTitle());
                message.setLinkDescription(meta.getDescription());
            }
        }
    }

    private MetaDto getMeta(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();

        Elements title = doc.select("meta[name$=title],meta[property$=title]");
        Elements description = doc.select("meta[name$=description],meta[property$=description]");
        Elements cover = doc.select("meta[name$=image],meta[property$=image]");

        return new MetaDto(
                getContent(title.first()),
                getContent(description.first()),
                getContent(cover.first())
        );
    }

    private String getContent(Element element) {
        return element == null ? "" : element.attr("content");
    }
}
