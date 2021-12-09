package com.onpu.web.store.entity;

import com.fasterxml.jackson.annotation.*;
import com.onpu.web.api.views.Views;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@EqualsAndHashCode(of = "id")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@ToString(of = "id")
public class UserSubscriptionEntity implements Serializable {
    @EmbeddedId
    @JsonIgnore
    UserSubscriptionId id;

    @MapsId("channelId")
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonView(Views.IdName.class)
    @JsonIdentityReference
    @JsonIdentityInfo(
            property = "id",
            generator = ObjectIdGenerators.PropertyGenerator.class
    )
    UserEntity channel;

    @MapsId("subscriberId")
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonView(Views.IdName.class)
    @JsonIdentityReference
    @JsonIdentityInfo(
            property = "id",
            generator = ObjectIdGenerators.PropertyGenerator.class
    )
    UserEntity subscriber;

    @JsonView(Views.IdName.class)
    boolean active;

    public UserSubscriptionEntity(UserEntity channel, UserEntity subscriber) {
        this.channel = channel;
        this.subscriber = subscriber;
        this.id = new UserSubscriptionId(channel.getId(), subscriber.getId());
    }
}
