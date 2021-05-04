package com.hanghae99.afterwork.service;

import com.hanghae99.afterwork.dto.ProductResponseDto;
import com.hanghae99.afterwork.model.Location;
import com.hanghae99.afterwork.model.Product;
import com.hanghae99.afterwork.model.User;
import com.hanghae99.afterwork.repository.ProductRepository;
import com.hanghae99.afterwork.repository.UserRepository;
import com.hanghae99.afterwork.security.UserPrincipal;
import org.springframework.stereotype.Service;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class RecommendService {

    private final UserRepository userRepository;

    @PersistenceContext
    EntityManager em;

    public RecommendService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public List<ProductResponseDto> recommendProduct(UserPrincipal userPrincipal){
        User user = userRepository.findByUserId(userPrincipal.getId());
        List<Location> locations = user.getLocations();
        if(locations.size() > 0){
            //JPQL query parameter builder
            StringBuilder jpql = new StringBuilder();
            for(int i = 0; i < locations.size(); i++){
                if(locations.size() == 1 && i == 0){
                    jpql.append("SELECT p FROM Product p WHERE p.location ");
                    jpql.append("LIKE '%" + locations.get(i).getName()+"%'" + " ORDER BY p.popularity DESC");
                }else if(locations.size() > 1){
                    if(i == 0){
                        jpql.append("SELECT p FROM Product p WHERE p.location ");
                        jpql.append("LIKE '%" + locations.get(i).getName()+"%' ");
                    }else if(i != locations.size()-1){
                        jpql.append("OR p.location LIKE '%" + locations.get(i).getName()+"%' ");
                    }else if(i == locations.size()-1){
                        jpql.append("OR p.location LIKE '%" + locations.get(i).getName()+"%' ORDER BY p.popularity DESC");
                    }
                }
            }
            Query query = em.createQuery(String.valueOf(jpql)).setMaxResults(12);
            List<Product> productList = query.getResultList();

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
}
