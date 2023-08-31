package com.restaurant.reservation.api.dto;

import com.restaurant.reservation.domain.CategoryMenu;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoryMenuApiDto {
    private Long id;
    private String categoryName;
    private String categoryCode;
    private String name;
    private Integer price;
    private String description;

    @Builder
    public CategoryMenuApiDto(Long id, String categoryName, String categoryCode, String name, Integer price, String description) {
        this.id = id;
        this.categoryName = categoryName;
        this.categoryCode = categoryCode;
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public static CategoryMenuApiDto of(CategoryMenu categoryMenu){
        return CategoryMenuApiDto.builder()
                .id(categoryMenu.getMenu().getId())
                .categoryName(categoryMenu.getCategory().getName())
                .categoryCode(categoryMenu.getCategory().getCode())
                .name(categoryMenu.getMenu().getName())
                .price(categoryMenu.getMenu().getPrice())
                .description(categoryMenu.getMenu().getDescription())
                .build();
    }
}
