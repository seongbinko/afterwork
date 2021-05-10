package com.hanghae99.afterwork.repository;

import com.hanghae99.afterwork.model.Category;
import com.hanghae99.afterwork.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "select p from Product p where p.category = :#{#category} and p.status = 'Y' and " +
            "(((:isOnline = true and :isOffline = false) and (p.isOnline = :isOnline and p.isOffline = :isOffline)) or " +
            "((:isOnline = false and :isOffline = true) and (p.isOnline = :isOnline and p.isOffline = :isOffline)) or" +
            "((:isOnline = true and :isOffline = true) and (p.isOnline = true or p.isOnline = false and p.isOffline = true or p.isOffline = false)))" +
            "group by p.title")
    Page<Product> findAllByCategoryAndOnlineAndLocation(Category category, Boolean isOnline, Boolean isOffline, Pageable pageable);

    @Query(value = "select p from Product p where p.title like :keyword and p.status = 'Y' and " +
            "(((:isOnline = true and :isOffline = false) and (p.isOnline = :isOnline and p.isOffline = :isOffline)) or " +
            "((:isOnline = false and :isOffline = true) and (p.isOnline = :isOnline and p.isOffline = :isOffline)) or" +
            "((:isOnline = true and :isOffline = true) and (p.isOnline = true or p.isOnline = false and p.isOffline = true or p.isOffline = false)))" +
            "group by p.title")
    Page<Product> findAllByTitleLikeAndOnlineAndLocation(String keyword, Boolean isOnline, Boolean isOffline, Pageable pageable);

    boolean existsByProductId(Long productId);
    Product findByProductId(Long productId);
    Optional<Product> findByTitleLikeAndCategory(String title, Category category);
    Optional<Product> findByTitleLikeAndCategoryAndLocation(String title, Category category, String location);
    List<Product>findAllBySiteName(String siteName);
}
