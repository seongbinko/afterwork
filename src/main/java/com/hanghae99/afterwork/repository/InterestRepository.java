package com.hanghae99.afterwork.repository;

import com.hanghae99.afterwork.entity.Interest;
import com.hanghae99.afterwork.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface InterestRepository extends JpaRepository<Interest, Long> {
    List<Interest> findAllByUser(User user);
}
