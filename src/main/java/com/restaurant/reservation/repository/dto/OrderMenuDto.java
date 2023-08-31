package com.restaurant.reservation.repository.dto;

import com.restaurant.reservation.api.response.MenuCountResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Builder
public class OrderMenuDto {

    // form -> id를 통해서 list로 만들어 질 것임
    private Long menuId;

//    private Integer price;
    private Integer count;

    public OrderMenuDto(){

    }



    public OrderMenuDto(Long menuId, int count) {
        this.menuId = menuId;
        this.count = count;
    }


    public OrderMenuDto(MenuCountResponse response) {
        this.menuId = response.getMenuId();
        this.count = response.getCount();
    }
}
