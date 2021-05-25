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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
        List<Location> locations = user.getLocations();
        if(locations.size() > 0){
            //JPQL query parameter builder
            StringBuilder jpql = new StringBuilder();
            for(int i = 0; i < locations.size(); i++){
                if(locations.size() == 1 && i == 0){
                    jpql.append("SELECT p FROM Product p WHERE p.status = 'Y' AND p.location ");
                    jpql.append("LIKE '%" + locations.get(i).getName()+"%'");
                }else if(locations.size() > 1){
                    if(i == 0){
                        jpql.append("SELECT p FROM Product p WHERE p.status = 'Y' AND p.location ");
                        jpql.append("LIKE '%" + locations.get(i).getName()+"%' ");
                    }else if(i != locations.size()-1){
                        jpql.append("OR p.location LIKE '%" + locations.get(i).getName()+"%' ");
                    }else if(i == locations.size()-1){
                        jpql.append("OR p.location LIKE '%" + locations.get(i).getName()+"%'");
                    }
                }
            }
            Query query = em.createQuery(String.valueOf(jpql));
            List<Product> foundList = query.getResultList();
            List<Product> productList = new ArrayList<>();

            int[] randomItem = getRandomItem(foundList.size());

            for(int i = 0; i < randomItem.length; i++){
                productList.add(foundList.get(randomItem[i]));
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
                                    product.isOffline(),
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

    public int[] getRandomItem (int foundListSize){

        int arrSize = 0;

        if(foundListSize >= 12){
            arrSize = 12;
        }else{
            arrSize = foundListSize;
        }

        Random ran = new Random();
        int[] arr = new int[arrSize];

        int j = 0;
        //뽑을 번호의 방 만큼 돌기
        while(j < arrSize){
            //긁어온 상품 리스트 크기 만큼 랜덤값 뽑기
            int rNum = ran.nextInt(foundListSize);
            int check = 1;
            int k = 0;
            //랜덤 중복 유효 검사
            while(k < j){
                if(rNum == arr[k]){
                    check = -1;
                }
                k += 1;
            }
            if(check == 1){
                arr[j] = rNum;
                j += 1;
            }
        }
        return arr;
    }
}
