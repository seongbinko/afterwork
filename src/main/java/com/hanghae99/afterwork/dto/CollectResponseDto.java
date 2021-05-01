package com.hanghae99.afterwork.dto;

import com.hanghae99.afterwork.model.Collect;
import lombok.Data;

@Data
public class CollectResponseDto {

    private Long collectId;
    private Long productId;
    private Long userId;

    public CollectResponseDto(Collect collect){
        this.collectId = collect.getCollectId();
        this.productId = collect.getProduct().getProductId();
        this.userId = collect.getUser().getUserId();
    }

    public CollectResponseDto(Long collectId, Long productId) {
        this.collectId = collectId;
        this.productId = productId;
    }

    public CollectResponseDto(Long collectId, Long productId, Long userId) {
        this.collectId = collectId;
        this.productId = productId;
    }

}
