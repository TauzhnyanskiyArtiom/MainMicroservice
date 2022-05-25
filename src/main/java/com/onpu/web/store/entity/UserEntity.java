package com.onpu.web.store.entity;

import com.fasterxml.jackson.annotation.*;
import com.onpu.web.api.views.Views;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
@Data
@EqualsAndHashCode(of = {"id"})
@ToString(exclude = {"subscriptions", "subscribers"})
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usr")
public class UserEntity implements Serializable {

    @Transient
    private static final long serialVersionUID = 1L;

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

    @Builder.Default
    @JsonView(Views.FullProfile.class)
    @OneToMany(
            mappedBy = "subscriber",
            orphanRemoval = true
    )
    private Set<UserSubscriptionEntity> subscriptions = new HashSet<>();

    @Builder.Default
    @JsonView(Views.FullProfile.class)
    @OneToMany(
            mappedBy = "channel",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    private Set<UserSubscriptionEntity> subscribers = new HashSet<>();


}
