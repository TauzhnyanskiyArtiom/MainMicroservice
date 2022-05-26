package com.onpu.web.store.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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
    String id;

    String name;
    String userpic;
    String email;
    String locale;
    LocalDateTime lastVisit;

    @Builder.Default
    @OneToMany(
            mappedBy = "subscriber",
            orphanRemoval = true
    )
    private Set<UserSubscriptionEntity> subscriptions = new HashSet<>();

    @Builder.Default
    @OneToMany(
            mappedBy = "channel",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    private Set<UserSubscriptionEntity> subscribers = new HashSet<>();


}
