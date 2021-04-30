package com.hanghae99.afterwork.repository;

import com.hanghae99.afterwork.model.Interest;
import com.hanghae99.afterwork.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterestRepository extends JpaRepository<Interest, Long> {
    List<Interest> findAllByUser(User user);
}
