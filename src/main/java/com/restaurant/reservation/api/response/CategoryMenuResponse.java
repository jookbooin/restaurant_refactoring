package com.restaurant.reservation.api.response;

import com.restaurant.reservation.domain.CategoryMenu;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoryMenuResponse {
    private Long id;
    private String categoryName;
    private String categoryCode;
    private String name;
    private Integer price;
    private String description;

    @Builder
    public CategoryMenuResponse(Long id, String categoryName, String categoryCode, String name, Integer price, String description) {
        this.id = id;
        this.categoryName = categoryName;
        this.categoryCode = categoryCode;
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public static CategoryMenuResponse of(CategoryMenu categoryMenu){
        return CategoryMenuResponse.builder()
                .id(categoryMenu.getMenu().getId())
                .categoryName(categoryMenu.getCategory().getName())
                .categoryCode(categoryMenu.getCategory().getCode())
                .name(categoryMenu.getMenu().getName())
                .price(categoryMenu.getMenu().getPrice())
                .description(categoryMenu.getMenu().getDescription())
                .build();
    }
}
