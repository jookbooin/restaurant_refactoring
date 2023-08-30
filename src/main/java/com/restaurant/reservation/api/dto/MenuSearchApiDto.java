package com.restaurant.reservation.api.dto;

import com.restaurant.reservation.domain.CategoryMenu;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class MenuSearchApiDto {
    private String searchName;
    private String searchCode;

    private List<MenuApiDto> menuList = new ArrayList<>();

    @Builder
    public MenuSearchApiDto(String searchName, String searchCode, List<MenuApiDto> menuList) {
        this.searchName = searchName;
        this.searchCode = searchCode;
        if (menuList.size() > 0)
            this.menuList = menuList;
    }

    public static MenuSearchApiDto of(String searchName , String searchCode , List<CategoryMenu> menuList){
        return MenuSearchApiDto.builder()
                .searchName(searchName)
                .searchCode(searchCode)
                .menuList(menuList.stream().map(MenuApiDto::of).collect(Collectors.toList()))
                .build();
    }

}
