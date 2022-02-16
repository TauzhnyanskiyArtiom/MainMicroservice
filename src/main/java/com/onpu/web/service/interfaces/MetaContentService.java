package com.onpu.web.service.interfaces;

import com.onpu.web.api.dto.MetaDto;
import com.onpu.web.store.entity.MessageEntity;

import java.io.IOException;

public interface MetaContentService {

    void fillMeta(MessageEntity message);

    MetaDto getMeta(String url) throws IOException;

}
