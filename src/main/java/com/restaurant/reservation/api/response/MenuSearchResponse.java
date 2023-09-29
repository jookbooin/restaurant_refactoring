package com.restaurant.reservation.api.response;

import com.restaurant.reservation.domain.CategoryMenu;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class MenuSearchResponse {
    private String searchName;
    private String searchCode;

    private List<CategoryMenuResponse> menuList = new ArrayList<>();

    @Builder
    public MenuSearchResponse(String searchName, String searchCode, List<CategoryMenuResponse> menuList) {
        this.searchName = searchName;
        this.searchCode = searchCode;
        if (menuList.size() > 0)
            this.menuList = menuList;
    }

    public static MenuSearchResponse of(String searchName , String searchCode , List<CategoryMenu> menuList){
        return MenuSearchResponse.builder()
                .searchName(searchName)
                .searchCode(searchCode)
                .menuList(menuList.stream().map(CategoryMenuResponse::responseFrom).collect(Collectors.toList()))
                .build();
    }

}
