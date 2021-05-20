package com.hanghae99.afterwork.dto;

import lombok.Data;

import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
public class UserRequestDto {

    @Size(max = 10)
    String offTime;

    @Size(max = 4)
    List<String> locations = new ArrayList<>();

    @Size(max = 7)
    List<Long> categorys = new ArrayList<>();
}
