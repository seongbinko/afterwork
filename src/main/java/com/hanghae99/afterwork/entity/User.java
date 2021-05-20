package com.hanghae99.afterwork.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@AllArgsConstructor @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String name;

    @Email
    @Column(nullable = false)
    private String email;

    private String password;

    private String imageUrl;

    private String offTime;

    @OneToMany(mappedBy = "user")
    @Builder.Default
    List<Location> locations = new ArrayList<>();

    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    private String providerId;

    @OneToMany(mappedBy = "user")
    @Builder.Default
    List<Interest> interests = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @Builder.Default
    List<Collect> collects = new ArrayList<>();
}
