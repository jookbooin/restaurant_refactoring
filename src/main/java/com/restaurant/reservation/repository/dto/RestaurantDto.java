package com.restaurant.reservation.repository.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RestaurantDto {
    private Long id;
    private String name;

    @Builder
    public RestaurantDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

}
