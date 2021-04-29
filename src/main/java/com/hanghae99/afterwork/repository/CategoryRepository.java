package com.hanghae99.afterwork.repository;

import com.hanghae99.afterwork.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByCategoryId(Long categoryId);
    Optional<Category> findByName(String name);

}
