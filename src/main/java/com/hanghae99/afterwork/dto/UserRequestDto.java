package com.hanghae99.afterwork.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserRequestDto {

    String offTime;
    List<String> locations = new ArrayList<>();
    List<Long> categorys = new ArrayList<>();
}
