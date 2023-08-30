package com.restaurant.reservation.api.dto;

import com.restaurant.reservation.domain.CategoryMenu;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MenuApiDto {
    private Long id;
    private String name;
    private Integer price;
    private String description;

    @Builder
    public MenuApiDto(Long id, String name, Integer price, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public static MenuApiDto of (CategoryMenu categoryMenu){
        return MenuApiDto.builder()
                .id(categoryMenu.getMenu().getId())
                .name(categoryMenu.getMenu().getName())
                .price(categoryMenu.getMenu().getPrice())
                .description(categoryMenu.getMenu().getDescription())
                .build();
    }
}
