package com.hanghae99.afterwork.service;

import com.hanghae99.afterwork.model.Category;
import com.hanghae99.afterwork.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;

}
