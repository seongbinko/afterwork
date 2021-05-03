package com.hanghae99.afterwork.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class FeedbackRequestDto {

    @NotBlank
    String content;
}
