package com.onpu.web.store.entity;

import com.fasterxml.jackson.annotation.*;
import com.onpu.web.api.views.Views;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Data
@EqualsAndHashCode( of = {"id"})
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usr")
public class UserEntity implements Serializable {
    @Id
    @JsonView(Views.IdName.class)
    String id;

    @JsonView(Views.IdName.class)
    String name;

    @JsonView(Views.IdName.class)
    String userpic;
    String email;

    @JsonView(Views.FullProfile.class)
    String locale;

    @JsonView(Views.FullProfile.class)
    LocalDateTime lastVisit;

    @JsonView(Views.FullProfile.class)
    @OneToMany(
            mappedBy = "subscriber",
            orphanRemoval = true
    )
    private Set<UserSubscriptionEntity> subscriptions = new HashSet<>();

    @JsonView(Views.FullProfile.class)
    @OneToMany(
            mappedBy = "channel",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    private Set<UserSubscriptionEntity> subscribers = new HashSet<>();
}
