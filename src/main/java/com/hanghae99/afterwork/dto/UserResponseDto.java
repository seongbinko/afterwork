package com.hanghae99.afterwork.dto;

import com.hanghae99.afterwork.model.Category;
import com.hanghae99.afterwork.model.Collect;
import com.hanghae99.afterwork.model.Interest;
import com.hanghae99.afterwork.model.Location;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserResponseDto {

    private Long userId;

    private String email;

    private String name;

    private String imageUrl;

    private String offTime;

    List<Location> locations;

    List<Interest> interests;

    List<Collect> collects;

    public UserResponseDto(Long userId, String email, String name, String imageUrl, String offTime, List<Location> locations, List<Interest> interests, List<Collect> collects) {
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.imageUrl = imageUrl;
        this.offTime = offTime;
        this.locations = locations;
        this.interests = interests;
        this.collects = collects;
    }
}
