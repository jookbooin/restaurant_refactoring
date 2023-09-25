package com.restaurant.reservation.web.webDto;

import com.restaurant.reservation.domain.CategoryMenu;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryMenuWeb {
    private Long categoryId;
    private String categoryName;
    private String categoryCode;
    private Long menuId;
    private String menuName;
    private int price;
    private String description;

    @Builder
    public CategoryMenuWeb(Long categoryId, String categoryName, String categoryCode, Long menuId, String menuName, int price, String description) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryCode = categoryCode;
        this.menuId = menuId;
        this.menuName = menuName;
        this.price = price;
        this.description = description;
    }

    public static CategoryMenuWeb categoryMenuFrom(CategoryMenu categoryMenu){
        CategoryMenuWeb web = CategoryMenuWeb.builder()
                .menuId(categoryMenu.getMenu().getId())
                .menuName(categoryMenu.getMenu().getName())
                .price(categoryMenu.getMenu().getPrice())
                .description(categoryMenu.getMenu().getDescription())
                .build();
        return web;
    }
}
