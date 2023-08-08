package com.restaurant.reservation.web.webDto;

import com.restaurant.reservation.domain.OrderMenu;
import lombok.Data;

/** Web - Controller 까지에서만 사용되는 메뉴 관리 Dto*/
@Data
public class OrderMenuWebDto {
    private Long menuId;
    private String name;
    private Integer orderPrice;
    private Integer count;

    public static OrderMenuWebDto createWebDto(OrderMenu orderMenu){
        OrderMenuWebDto orderMenuWebDto = new OrderMenuWebDto();
        orderMenuWebDto.setMenuId(orderMenu.getId());
        orderMenuWebDto.setName(orderMenu.getMenu().getName());
        orderMenuWebDto.setCount(orderMenu.getCount());
        orderMenuWebDto.setOrderPrice(orderMenu.getOrderPrice());
        return orderMenuWebDto;
    }
}
