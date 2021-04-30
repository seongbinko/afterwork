package com.hanghae99.afterwork.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CollectRequestDto {

    @NotNull
    private Long productId;
}
