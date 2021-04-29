package com.hanghae99.afterwork.repository;

import com.hanghae99.afterwork.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
}
