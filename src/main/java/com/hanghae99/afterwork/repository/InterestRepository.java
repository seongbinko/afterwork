package com.hanghae99.afterwork.repository;

import com.hanghae99.afterwork.model.Interest;
import com.hanghae99.afterwork.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterestRepository extends JpaRepository<Interest, Long> {
    List<Interest> findAllByUser(User user);
}
