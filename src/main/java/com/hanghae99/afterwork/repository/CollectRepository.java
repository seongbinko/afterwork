package com.hanghae99.afterwork.repository;

import com.hanghae99.afterwork.model.Collect;
import com.hanghae99.afterwork.model.Product;
import com.hanghae99.afterwork.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CollectRepository extends JpaRepository<Collect, Long> {

    void deleteAllByUser(User user);
    boolean existsByCollectId(Long collectId);
    void deleteAllByProduct(Product product);
    void deleteByCollectId(Long collectId);
    List<Collect> findAllByUser(User user);
}
