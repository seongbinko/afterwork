package com.hanghae99.afterwork.repository;

import com.hanghae99.afterwork.model.Collect;
import com.hanghae99.afterwork.model.Product;
import com.hanghae99.afterwork.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CollectRepository extends JpaRepository<Collect, Long> {
    void deleteAllByUser(User user);
    void deleteByCollectId(Long collectId);
    List<Collect> findAllByUser(User user);
    boolean existsByUserAndProduct (User user, Product product);
}
