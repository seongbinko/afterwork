package com.hanghae99.afterwork.repository;

import com.hanghae99.afterwork.model.Collect;
import com.hanghae99.afterwork.model.Product;
import com.hanghae99.afterwork.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectRepository extends JpaRepository<Collect, Long> {

    void deleteAllByUser(User user);
    boolean existsByCollectId(Long collectId);
    void deleteAllByProduct(Product product);
    void deleteByCollectId(Long collectId);
}
