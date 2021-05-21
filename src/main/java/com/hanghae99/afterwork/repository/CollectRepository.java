package com.hanghae99.afterwork.repository;

import com.hanghae99.afterwork.entity.Collect;
import com.hanghae99.afterwork.entity.Product;
import com.hanghae99.afterwork.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface CollectRepository extends JpaRepository<Collect, Long> {

    @Transactional
    void deleteAllByUser(User user);

    @Transactional
    void deleteByCollectId(Long collectId);

    List<Collect> findAllByUser(User user);

    boolean existsByUserAndProduct(User user, Product product);

    Collect findByProductAndUser(Product product, User user);

    Integer countAllByUser(User user);
}
