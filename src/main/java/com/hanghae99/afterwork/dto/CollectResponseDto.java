package com.hanghae99.afterwork.dto;

import com.hanghae99.afterwork.model.Collect;
import lombok.Data;

@Data
public class CollectResponseDto {

    public CollectResponseDto(Collect collect){
        this.collectId = collect.getCollectId();
        this.productId = collect.getProduct().getProductId();
//        this.userId = collect.getUser().getUserId();
    }
    private Long collectId;
    private Long productId;
//    private Long userId;
}
