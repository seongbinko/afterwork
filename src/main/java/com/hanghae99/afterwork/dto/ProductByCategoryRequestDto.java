package com.hanghae99.afterwork.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class ProductByCategoryRequestDto {

    @NotNull
    @Max(1000)
    private int page;

    @NotNull
    @Max(100)
    private int size;

    @NotBlank
    @Pattern(regexp = "^[a-z]{5,10}$")
    private String sort;

    @NotBlank
    @Pattern(regexp = "^[a-z]{3,4}$")
    private String direction;

    @NotBlank
    @Pattern(regexp = "^[a-z]{5,7}$")
    private String filter;

    @NotBlank
    @Pattern(regexp = "^[가-힣,0-9]{2,100}$")
    private String sitename;

    @NotBlank
    @Pattern(regexp = "^[^\\{\\}\\[\\]\\/?.;:|\\)*~`!\\^\\\\\\-_+<>@#$%&'\\(=\"]{0,100}$")
    private String location;

}
