package com.hanghae99.afterwork.repository;

import com.hanghae99.afterwork.model.Category;
import com.hanghae99.afterwork.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findAllByCategory(Category category, Pageable pageable);
    Page<Product> findAllByTitleLike(String keyword, Pageable pageable);
    boolean existsByProductId(Long productId);
    Product findByProductId(Long productId);
}
