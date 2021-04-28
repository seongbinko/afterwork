package com.hanghae99.afterwork.repository;

import com.hanghae99.afterwork.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findById(Long id);
    Optional<Category> findByName(String name);

}
