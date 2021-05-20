package com.hanghae99.afterwork.repository;

import com.hanghae99.afterwork.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    Feedback findByContent(String content);
}
