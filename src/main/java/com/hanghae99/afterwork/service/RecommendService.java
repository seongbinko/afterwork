package com.hanghae99.afterwork.service;

import com.hanghae99.afterwork.dto.ProductResponseDto;
import com.hanghae99.afterwork.entity.Interest;
import com.hanghae99.afterwork.entity.Location;
import com.hanghae99.afterwork.entity.Product;
import com.hanghae99.afterwork.entity.User;
import com.hanghae99.afterwork.exception.ResourceNotFoundException;
import com.hanghae99.afterwork.repository.ProductRepository;
import com.hanghae99.afterwork.repository.UserRepository;
import com.hanghae99.afterwork.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RecommendService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ProductService productService;

    @PersistenceContext
    EntityManager em;

    public static final int RECOMMENDSIZE = 12;

    public List<ProductResponseDto> recommendProduct(UserPrincipal userPrincipal){
        User user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
        List<Location> locationsList = user.getLocations();
        //JPQL query parameter builder
        StringBuilder jpql = new StringBuilder();
        jpql.append("SELECT p FROM Product p WHERE p.status = 'Y' ");
        for(int i = 0; i < locationsList.size(); i++){
            if(i == 0){
                jpql.append("AND p.location LIKE '%" + locationsList.get(i).getName()+"%' ");
            }else{
                jpql.append("OR p.location LIKE '%" + locationsList.get(i).getName()+"%' ");
            }
        }
        jpql.append(" ORDER BY rand()");
        Query query = em.createQuery(String.valueOf(jpql));
        query.setMaxResults(RECOMMENDSIZE);
        List<Product> productList = query.getResultList();

        return productService.createProductResponseDtoList(productList);
    }

    public List<ProductResponseDto> recommendCategoryProduct(UserPrincipal userPrincipal){
        User user = userRepository.findByUserId(userPrincipal.getId());
        List<Interest> interestList = user.getInterests();
        //JPQL query parameter builder
        StringBuilder jpql = new StringBuilder();
        jpql.append("SELECT p FROM Product p WHERE p.status = 'Y'");
        for(int i = 0; i < interestList.size(); i++){
            if (i == 0){
                jpql.append(" AND p.category = ").append(interestList.get(i).getCategory().getCategoryId());
            } else{
                jpql.append(" OR p.category = ").append(interestList.get(i).getCategory().getCategoryId());
            }
        }
        jpql.append(" ORDER BY rand()");
        Query query = em.createQuery(String.valueOf(jpql));
        query.setMaxResults(RECOMMENDSIZE);
        List<Product> productList = query.getResultList();

        return productService.createProductResponseDtoList(productList);
    }

    public List<ProductResponseDto> recommendOnlineProduct(){
        List<Product> productList = productRepository.findByRecommendOnline();
        return productService.createProductResponseDtoList(productList);
    }

    public List<ProductResponseDto> recommendOfflineProduct(){
        List<Product> productList = productRepository.findByRecommendOffline();
        return productService.createProductResponseDtoList(productList);
    }
}
