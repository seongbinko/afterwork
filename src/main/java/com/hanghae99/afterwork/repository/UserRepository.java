package com.hanghae99.afterwork.repository;

import com.hanghae99.afterwork.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserId(Long id);
    Optional<User> findByEmail(String email);

}
