package com.hanghae99.afterwork.dto;

import lombok.Data;

@Data
public class InterestResponseDto {

    private Long interestId;

    private Long categoryId;

    public InterestResponseDto(Long interestId, Long categoryId) {
        this.interestId = interestId;
        this.categoryId = categoryId;
    }
}
