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
    @Query(value = "select * from products " +
            "where category_id = :#{#category.categoryId} and status = 'Y' and if(:location IS Not null, if(:isOnline = true, true, is_online = :isOnline), is_online = :isOnline) " +
            "and if(:isOnline = true, if(:location IS null, location IS NULL or location like '%온라인%', true) , location IS NOT NULL) group by title",nativeQuery = true)
    Page<Product> findAllByCategoryAndOnlineAndLocation(Category category, Boolean isOnline, String location, Pageable pageable);

    @Query(value = "select * from products " +
            "where title like :keyword and status = 'Y' and if(:location IS Not null, if(:isOnline = true, true, is_online = :isOnline), is_online = :isOnline) " +
            "and if(:isOnline = true, if(:location IS null, location IS NULL or location like '%온라인%', true) , location IS NOT NULL) group by title",nativeQuery = true)
    Page<Product> findAllByTitleLikeAndOnlineAndLocation(String keyword, Boolean isOnline, String location, Pageable pageable);

    boolean existsByProductId(Long productId);
    Product findByProductId(Long productId);
    Optional<Product> findByTitleLikeAndCategory(String title, Category category);
    Optional<Product> findByTitleAndCategoryAndLocation(String title, Category category, String location);
    List<Product>findAllBySiteName(String siteName);
}
