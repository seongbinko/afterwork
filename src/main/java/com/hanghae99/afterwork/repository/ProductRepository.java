package com.hanghae99.afterwork.repository;

import com.hanghae99.afterwork.entity.Category;
import com.hanghae99.afterwork.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "select p from Product p where p.category = :#{#category} and p.status = 'Y' and " +
            "(((:isOnline = true and :isOffline = false) and (p.isOnline = :isOnline)) or " +
            "((:isOnline = false and :isOffline = true) and (p.isOffline = :isOffline)) or" +
            "((:isOnline = true and :isOffline = true) and (p.isOnline = true or p.isOnline = false and p.isOffline = true or p.isOffline = false))) and" +
            "(((:isTaling = true) and (p.siteName like '탈잉')) or" +
            "((:isClass101 = true) and (p.siteName like '클래스101')) or" +
            "((:isHobyInTheBox = true) and (p.siteName like '하비인더박스')) or" +
            "((:isIdus = true) and (p.siteName like '아이디어스')) or" +
            "((:isMybiskit = true) and (p.siteName like '마이비스킷')) or" +
            "((:isMochaClass = true) and (p.siteName like '모카클래스')) or" +
            "((:isHobbyful = true) and (p.siteName like '하비풀')))" +
            "group by p.title")
    Page<Product> findAllByCategoryAndOnline(@Param("category") Category category, @Param("isOnline") Boolean isOnline,
                                             @Param("isOffline") Boolean isOffline, @Param("isTaling") Boolean isTaling,
                                             @Param("isClass101") Boolean isClass101, @Param("isHobyInTheBox") Boolean isHobyInTheBox,
                                             @Param("isIdus") Boolean isIdus, @Param("isMybiskit") Boolean isMybiskit,
                                             @Param("isMochaClass") Boolean isMochaClass, @Param("isHobbyful") Boolean isHobbyful,
                                             Pageable pageable);

    @Query(value = "select p from Product p where p.title like :keyword and p.status = 'Y' and " +
            "(((:isOnline = true and :isOffline = false) and (p.isOnline = :isOnline)) or " +
            "((:isOnline = false and :isOffline = true) and (p.isOffline = :isOffline)) or" +
            "((:isOnline = true and :isOffline = true) and (p.isOnline = true or p.isOnline = false and p.isOffline = true or p.isOffline = false))) and" +
            "(((:isTaling = true) and (p.siteName like '탈잉')) or" +
            "((:isClass101 = true) and (p.siteName like '클래스101')) or" +
            "((:isHobyInTheBox = true) and (p.siteName like '하비인더박스')) or" +
            "((:isIdus = true) and (p.siteName like '아이디어스')) or" +
            "((:isMybiskit = true) and (p.siteName like '마이비스킷')) or" +
            "((:isMochaClass = true) and (p.siteName like '모카클래스')) or" +
            "((:isHobbyful = true) and (p.siteName like '하비풀')))" +
            "group by p.title")
    Page<Product> findAllByTitleLikeAndOnline(@Param("keyword")String keyword, @Param("isOnline") Boolean isOnline,
                                              @Param("isOffline") Boolean isOffline, @Param("isTaling") Boolean isTaling,
                                              @Param("isClass101") Boolean isClass101, @Param("isHobyInTheBox") Boolean isHobyInTheBox,
                                              @Param("isIdus") Boolean isIdus, @Param("isMybiskit") Boolean isMybiskit,
                                              @Param("isMochaClass") Boolean isMochaClass, @Param("isHobbyful") Boolean isHobbyful,
                                              Pageable pageable);

    @Query("select p from Product p where p.isRecommendOnline = true")
    List<Product> findByRecommendOnline();

    @Query("select p from Product p where p.isRecommendOffline = true")
    List<Product> findByRecommendOffline();

    boolean existsByProductId(Long productId);
    Product findByProductId(Long productId);
    Optional<Product> findByTitleLikeAndCategory(String title, Category category);
    Optional<Product> findByTitleLikeAndCategoryAndLocation(String title, Category category, String location);
    List<Product>findAllBySiteName(String siteName);
}
