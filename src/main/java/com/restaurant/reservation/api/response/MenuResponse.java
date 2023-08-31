package com.restaurant.reservation.api.response;

import com.restaurant.reservation.domain.CategoryMenu;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MenuResponse {
    private Long id;
    private String name;
    private Integer price;
    private String description;

    @Builder
    public MenuResponse(Long id, String name, Integer price, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public static MenuResponse of (CategoryMenu categoryMenu){
        return MenuResponse.builder()
                .id(categoryMenu.getMenu().getId())
                .name(categoryMenu.getMenu().getName())
                .price(categoryMenu.getMenu().getPrice())
                .description(categoryMenu.getMenu().getDescription())
                .build();
    }
}
