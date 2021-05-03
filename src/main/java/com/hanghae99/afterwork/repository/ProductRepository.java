package com.hanghae99.afterwork.repository;

import com.hanghae99.afterwork.model.Category;
import com.hanghae99.afterwork.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findAllByCategory(Category category, Pageable pageable);

    @Query(value = "select * from products where title like :keyword group by title",nativeQuery = true)
    Page<Product> findAllByTitleLike(@Param("keyword") String keyword, Pageable pageable);
    boolean existsByProductId(Long productId);
    Product findByProductId(Long productId);
}
