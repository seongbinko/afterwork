package com.hanghae99.afterwork.validator;

import com.hanghae99.afterwork.dto.CollectRequestDto;
import com.hanghae99.afterwork.model.Product;
import com.hanghae99.afterwork.repository.CollectRepository;
import com.hanghae99.afterwork.repository.ProductRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CollectRequestDtoValidator implements Validator {

    private final ProductRepository productRepository;
    private final CollectRepository collectRepository;

    public CollectRequestDtoValidator(ProductRepository productRepository, CollectRepository collectRepository){
        this.productRepository = productRepository;
        this.collectRepository = collectRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(CollectRequestDto.class);
    }

    @Override
    public void validate(Object target, Errors errors){
        CollectRequestDto collectRequestDto = (CollectRequestDto) target;
        Product product = productRepository.findByProductId(collectRequestDto.getProductId());

        if(!productRepository.existsByProductId(collectRequestDto.getProductId())){
            errors.rejectValue("productId", "invalid.productId", new Object[]{collectRequestDto.getProductId()},"존재하지 않는 상품 입니다");
        }
        if(collectRepository.existsByProduct(product)){
            errors.rejectValue("productId", "invalid.productId", new Object[]{collectRequestDto.getProductId()},"이미 찜해 놓은 상품 입니다");
        }
    }
}
