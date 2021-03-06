package com.hanghae99.afterwork.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserResponseDto {

    private Long userId;

    private String email;

    private String name;

    private String imageUrl;

    private String offTime;

    List<LocationResponseDto> locations;

    List<InterestResponseDto> interests;

    List<CollectResponseDto> collects;

    public UserResponseDto() {}

    public UserResponseDto(Long userId, String email, String name, String imageUrl, String offTime, List<LocationResponseDto> locations, List<InterestResponseDto> interests, List<CollectResponseDto> collects) {
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
