package com.hanghae99.afterwork.service;

import com.hanghae99.afterwork.dto.ProductResponseDto;
import com.hanghae99.afterwork.model.Location;
import com.hanghae99.afterwork.model.Product;
import com.hanghae99.afterwork.model.User;
import com.hanghae99.afterwork.repository.ProductRepository;
import com.hanghae99.afterwork.repository.UserRepository;
import com.hanghae99.afterwork.security.UserPrincipal;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class RecommendService {

    private final ProductRepository productRepository;

    private final UserRepository userRepository;


    public RecommendService(ProductRepository productRepository, UserRepository userRepository){
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public List<ProductResponseDto> recommendProduct(UserPrincipal userPrincipal){
        Sort sort = sortBypopularity();
        
        User user = userRepository.findByUserId(userPrincipal.getId());
        List<Location> locations = user.getLocations();
        List<Product> productList = new ArrayList<>();
        if(locations.size() > 0){
            for(int i = 0; i < locations.size(); i++){
                List<Product> temp = productRepository.findTop12ByLocationContains(locations.get(i).getName(), sort);
                productList.addAll(temp);
            }
            List<ProductResponseDto> productResponseDtoList =
                    productList.stream().map(
                            product -> new ProductResponseDto(
                                    product.getProductId(),
                                    product.getTitle(),
                                    product.getPrice(),
                                    product.getPriceInfo(),
                                    product.getAuthor(),
                                    product.getImgUrl(),
                                    product.isOnline(),
                                    product.getLocation(),
                                    product.getPopularity(),
                                    product.getStatus(),
                                    product.getSiteName(),
                                    product.getSiteUrl()
                            )).collect(Collectors.toList());
            return productResponseDtoList;
        }
        return null;
    }

    private Sort sortBypopularity() {
        return Sort.by(Sort.Direction.DESC, "popularity");
    }
}
