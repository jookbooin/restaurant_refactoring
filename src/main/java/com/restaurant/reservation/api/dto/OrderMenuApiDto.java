package com.restaurant.reservation.api.dto;

import com.restaurant.reservation.domain.OrderMenu;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class OrderMenuApiDto {
    private Long menuId;
    private String name;
    private Integer totalPrice;
    private Integer count;

    public static OrderMenuApiDto createWebDto(OrderMenu orderMenu){
        OrderMenuApiDto orderMenuApiDto = new OrderMenuApiDto();
        orderMenuApiDto.setMenuId(orderMenu.getId());
        orderMenuApiDto.setName(orderMenu.getMenu().getName());
        orderMenuApiDto.setCount(orderMenu.getCount());
        orderMenuApiDto.setTotalPrice(orderMenu.getTotalPrice());
        return orderMenuApiDto;
    }
}
