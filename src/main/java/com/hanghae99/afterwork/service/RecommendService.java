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
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RecommendService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @PersistenceContext
    EntityManager em;

    public static final int RECOMMENDSIZE = 12;

    public List<ProductResponseDto> recommendProduct(UserPrincipal userPrincipal){
        User user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
        List<Location> locationsList = user.getLocations();
        if(!locationsList.isEmpty()){
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

            return productList.stream().map(
                    product -> new ProductResponseDto(
                            product.getProductId(),
                            product.getTitle(),
                            product.getPrice(),
                            product.getPriceInfo(),
                            product.getAuthor(),
                            product.getImgUrl(),
                            product.isOnline(),
                            product.isOffline(),
                            product.getLocation(),
                            product.getPopularity(),
                            product.getStatus(),
                            product.getSiteName(),
                            product.getSiteUrl()
                    )).collect(Collectors.toList());
        }
        return null;
    }

    public List<ProductResponseDto> recommendCategoryProduct(UserPrincipal userPrincipal){
        User user = userRepository.findByUserId(userPrincipal.getId());
        List<Interest> interestList = user.getInterests();
        if(!interestList.isEmpty()){
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

            return productList.stream().map(
                    product -> new ProductResponseDto(
                            product.getProductId(),
                            product.getTitle(),
                            product.getPrice(),
                            product.getPriceInfo(),
                            product.getAuthor(),
                            product.getImgUrl(),
                            product.isOnline(),
                            product.isOffline(),
                            product.getLocation(),
                            product.getPopularity(),
                            product.getStatus(),
                            product.getSiteName(),
                            product.getSiteUrl()
                    )).collect(Collectors.toList());
        }
        return null;
    }

    public List<ProductResponseDto> recommendOnlineProduct(){
        List<Product> productList = productRepository.findByRecommendOnline();

        return productList.stream().map(
                product -> new ProductResponseDto(
                        product.getProductId(),
                        product.getTitle(),
                        product.getPrice(),
                        product.getPriceInfo(),
                        product.getAuthor(),
                        product.getImgUrl(),
                        product.isOnline(),
                        product.isOffline(),
                        product.getLocation(),
                        product.getPopularity(),
                        product.getStatus(),
                        product.getSiteName(),
                        product.getSiteUrl()
                )).collect(Collectors.toList());
    }

    public List<ProductResponseDto> recommendOfflineProduct(){
        List<Product> productList = productRepository.findByRecommendOffline();

        return productList.stream().map(
                product -> new ProductResponseDto(
                        product.getProductId(),
                        product.getTitle(),
                        product.getPrice(),
                        product.getPriceInfo(),
                        product.getAuthor(),
                        product.getImgUrl(),
                        product.isOnline(),
                        product.isOffline(),
                        product.getLocation(),
                        product.getPopularity(),
                        product.getStatus(),
                        product.getSiteName(),
                        product.getSiteUrl()
                )).collect(Collectors.toList());
    }
}
