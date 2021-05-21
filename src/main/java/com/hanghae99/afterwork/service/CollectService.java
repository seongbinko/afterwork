package com.hanghae99.afterwork.service;

import com.hanghae99.afterwork.dto.CollectRequestDto;
import com.hanghae99.afterwork.dto.ProductResponseDto;
import com.hanghae99.afterwork.entity.Collect;
import com.hanghae99.afterwork.entity.Product;
import com.hanghae99.afterwork.entity.User;
import com.hanghae99.afterwork.exception.BadRequestException;
import com.hanghae99.afterwork.exception.ResourceNotFoundException;
import com.hanghae99.afterwork.repository.CollectRepository;
import com.hanghae99.afterwork.repository.ProductRepository;
import com.hanghae99.afterwork.repository.UserRepository;
import com.hanghae99.afterwork.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class CollectService {

    private final ProductRepository productRepository;
    private final CollectRepository collectRepository;
    private final UserRepository userRepository;

    public Collect postCollect(CollectRequestDto collectRequestDto, UserPrincipal userPrincipal){
        Product product = productRepository.findById(collectRequestDto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product", "product", collectRequestDto.getProductId()));
        User user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
        if(collectRepository.existsByUserAndProduct(user, product)){
            throw new BadRequestException("이미 등록 하신 상품 입니다");
        }

        int collectsCnt = collectRepository.countAllByUser(user);

        if(collectsCnt == 50){
            throw new BadRequestException("상품을 50개 이상 찜 하실 수 없습니다");
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
                () -> new BadRequestException("해당 찜 상품이 존재하지 않습니다.")
        );
        User user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));

        if (collect.getUser().getUserId().equals(user.getUserId())) {
            collectRepository.deleteByCollectId(collectId);
        } else {
            throw new BadRequestException("등록 하지 않은 상품 입니다");
        }
        return collect;
    }

    public List<ProductResponseDto> getAllCollect(UserPrincipal userPrincipal) {
        User user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
        List<Collect> collects = collectRepository.findAllByUser(user);
        List<ProductResponseDto> products = new ArrayList<>();
        for (Collect collect : collects) {
            Product product = productRepository.findByProductId(collect.getProduct().getProductId());
            ProductResponseDto p = new ProductResponseDto(product, collect.getCollectId());
            products.add(p);
        }
        return products;
    }


}
