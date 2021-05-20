package com.hanghae99.afterwork.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "feedbacks")
@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Feedback extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedbackId;

    @Column(nullable = false)
    private String content;
}
