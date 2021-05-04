package com.hanghae99.afterwork.dto;

import lombok.Data;

@Data
public class LocationResponseDto {

    private Long locationId;
    private String name;

    public LocationResponseDto(Long locationId, String name) {
        this.locationId = locationId;
        this.name = name;
    }
}
