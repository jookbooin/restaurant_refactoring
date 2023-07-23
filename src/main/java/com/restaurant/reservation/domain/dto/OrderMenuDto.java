package com.restaurant.reservation.domain.dto;

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

    private Integer price;
    private Integer count;

    public OrderMenuDto(){

    }

    public OrderMenuDto(Long menuId, int price, int count) {
        this.menuId = menuId;
        this.price = price;
        this.count = count;
    }

    public OrderMenuDto(Long menuId, int count) {
        this.menuId = menuId;
        this.count = count;
    }
}
