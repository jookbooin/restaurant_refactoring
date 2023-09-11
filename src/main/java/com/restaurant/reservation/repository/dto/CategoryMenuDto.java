package com.restaurant.reservation.repository.dto;

import com.restaurant.reservation.api.request.form.MenuUpdateRequest;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryMenuDto {
    private Long id;
    private Long categoryId;
    private String categoryName;
    private String categoryCode;
    private Long menuId;
    private String menuName;
    private int price;
    private String description;

    @Builder
    public CategoryMenuDto(Long id, String categoryName, String categoryCode, Long menuId, String menuName, int price, String description) {
        this.id = id;
        this.categoryName = categoryName;
        this.categoryCode = categoryCode;
        this.menuId = menuId;
        this.menuName = menuName;
        this.price = price;
        this.description = description;
    }




//    public static CategoryMenuDto of(CategoryMenu categoryMenu){
//
//        return null;
//    }

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
