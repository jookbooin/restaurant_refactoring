package com.restaurant.reservation.repository.dto;

import com.restaurant.reservation.api.request.form.MenuUpdateRequest;
import com.restaurant.reservation.domain.enumType.MenuType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryMenuDto {
    private Long id;
    private String categoryName;
    private String categoryCode;
    private String menuName;
    private int price;
    private String description;
    private MenuType menuType;

    @Builder
    public CategoryMenuDto(Long id, String categoryName, String categoryCode, String menuName, int price, String description, MenuType menuType) {
        this.id = id;
        this.categoryName = categoryName;
        this.categoryCode = categoryCode;
        this.menuName = menuName;
        this.price = price;
        this.description = description;
        this.menuType = menuType;
    }

    public static CategoryMenuDto of (Long id ,MenuUpdateRequest menuUpdateRequest){

        return CategoryMenuDto.builder()
                .id(id)
                .categoryName(menuUpdateRequest.getCategoryName())
                .menuName(menuUpdateRequest.getMenuName())
                .price(menuUpdateRequest.getMenuPrice())
                .description(menuUpdateRequest.getDescription())
                .build();
    }


}
