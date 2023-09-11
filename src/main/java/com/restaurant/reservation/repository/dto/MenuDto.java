package com.restaurant.reservation.repository.dto;

import lombok.*;

@ToString
@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MenuDto {
    private Long id;
    private String name;
    private int price;
    private String description;
//    private MenuType menuType;

    @Builder
    public MenuDto(Long id, String name, int price, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
    }
}
