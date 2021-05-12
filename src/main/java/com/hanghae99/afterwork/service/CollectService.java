package com.hanghae99.afterwork.service;

import com.hanghae99.afterwork.dto.CollectRequestDto;
import com.hanghae99.afterwork.dto.ProductResponseDto;
import com.hanghae99.afterwork.exception.ResourceNotFoundException;
import com.hanghae99.afterwork.model.Collect;
import com.hanghae99.afterwork.model.Product;
import com.hanghae99.afterwork.model.User;
import com.hanghae99.afterwork.repository.CategoryRepository;
import com.hanghae99.afterwork.repository.CollectRepository;
import com.hanghae99.afterwork.repository.ProductRepository;
import com.hanghae99.afterwork.repository.UserRepository;
import com.hanghae99.afterwork.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class CollectService {

    private final ProductRepository productRepository;
    private final CollectRepository collectRepository;
    private final UserRepository userRepository;

    public Collect postCollect(CollectRequestDto collectRequestDto, UserPrincipal userPrincipal){
        Product product = productRepository.findById(collectRequestDto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product", "product", collectRequestDto.getProductId()));;
        User user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
        if(collectRepository.existsByUserAndProduct(user, product)){
            throw new NullPointerException("이미 등록 하신 상품 입니다");
        }
        Collect collect = Collect.builder()
                .product(product)
                .user(user)
                .build();

        return collectRepository.save(collect);
    }

    public void deleteAllCollect(UserPrincipal userPrincipal){
        User user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
        collectRepository.deleteAllByUser(user);
    }

    public Collect deleteOneCollect(Long collectId, UserPrincipal userPrincipal){
        Collect collect = collectRepository.findById(collectId).orElseThrow(
                () -> new NullPointerException("해당 찜 상품이 존재하지 않습니다.")
        );
        User user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));

        if(collect.getUser().getUserId() == user.getUserId()){
            collectRepository.deleteByCollectId(collectId);
        }else{
            throw new NullPointerException("등록 하지 않은 상품 입니다");
        }
        return collect;
    }

    public List<ProductResponseDto> getAllCollect(UserPrincipal userPrincipal){
        User user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
        List<Collect> collects = collectRepository.findAllByUser(user);
        List<ProductResponseDto> products = new ArrayList<>();
        for(int i = 0; i < collects.size(); i++){
            Product product = productRepository.findByProductId(collects.get(i).getProduct().getProductId());
            ProductResponseDto p = new ProductResponseDto(product, collects.get(i).getCollectId());
            products.add(p);
        }
        return products;
    }


}
