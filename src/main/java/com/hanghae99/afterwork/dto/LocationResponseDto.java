package com.hanghae99.afterwork.dto;

public class LocationResponseDto {

    private Long locationId;
    private String name;

    public LocationResponseDto(Long locationId, String name) {
        this.locationId = locationId;
        this.name = name;
    }
}
