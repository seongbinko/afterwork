package com.hanghae99.afterwork.service;

import com.hanghae99.afterwork.dto.CollectRequestDto;
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
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;

@RequiredArgsConstructor
@Service
@Transactional
public class CollectService {

    private final ProductRepository productRepository;
    private final CollectRepository collectRepository;
    private final UserRepository userRepository;

    public Collect postCollect(CollectRequestDto collectRequestDtorequestDto, UserPrincipal userPrincipal){
        Product product = productRepository.findByProductId(collectRequestDtorequestDto.getProductId());
        User user = userRepository.findByUserId(userPrincipal.getId());

        Collect collect = Collect.builder()
                .product(product)
                .user(user)
                .build();

        return collectRepository.save(collect);
    }

    public void deleteAllCollect(UserPrincipal userPrincipal){
        User user = userRepository.findByUserId(userPrincipal.getId());
        collectRepository.deleteAllByUser(user);
    }

    public Collect deleteOneCollect(Long collectId, UserPrincipal userPrincipal){
        Collect collect = collectRepository.findById(collectId).orElseThrow(
                () -> new NullPointerException("해당 찜 목록이 존재하지 않습니다.")
        );
        User user = userRepository.findByUserId(userPrincipal.getId());

        int index = 0;
        for(int i = 0; i < user.getCollects().size(); i++){
            if(user.getCollects().get(i).getCollectId() == collectId){
                index = i;
                break;
            }
        }
        if(collectId != user.getCollects().get(index).getCollectId()){
            collectRepository.deleteByCollectId(collectId);
        }
        return collect;
    }
}
