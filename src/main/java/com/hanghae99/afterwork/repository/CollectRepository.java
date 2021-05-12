package com.hanghae99.afterwork.repository;

import com.hanghae99.afterwork.model.Collect;
import com.hanghae99.afterwork.model.Product;
import com.hanghae99.afterwork.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CollectRepository extends JpaRepository<Collect, Long> {
    void deleteAllByUser(User user);
    void deleteByCollectId(Long collectId);
    List<Collect> findAllByUser(User user);
    boolean existsByUserAndProduct (User user, Product product);
    Collect findByProductAndUser(Product product, User user);
}
