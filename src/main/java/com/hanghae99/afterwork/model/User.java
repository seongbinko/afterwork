package com.hanghae99.afterwork.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@AllArgsConstructor @Builder
@NoArgsConstructor//(access = AccessLevel.PROTECTED)// protected로 기본생성자 생성
@ToString(of = {"id", "name", "email", "offTime", "imgUrl", "location"})
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Email
    @Column(nullable = false)
    private String email;

    private String password;

    private String imageUrl;

    private LocalDateTime offTime;

    private String location;

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
