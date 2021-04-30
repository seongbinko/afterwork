package com.hanghae99.afterwork.repository;

import com.hanghae99.afterwork.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserId(Long id);
    Optional<User> findByEmail(String email);

}
