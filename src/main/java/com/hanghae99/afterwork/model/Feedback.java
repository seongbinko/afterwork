package com.hanghae99.afterwork.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "feedbacks")
@Getter @Setter
@AllArgsConstructor @Builder
@NoArgsConstructor (access = AccessLevel.PROTECTED)// protected로 기본생성자 생성
public class Feedback extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedbackId;

    @Column(nullable = false)
    private String content;
}
