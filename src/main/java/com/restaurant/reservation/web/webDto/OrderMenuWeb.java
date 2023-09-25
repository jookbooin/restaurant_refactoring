package com.restaurant.reservation.web.webDto;

import com.restaurant.reservation.domain.OrderMenu;
import lombok.Data;

/** Web - Controller 까지에서만 사용되는 메뉴 관리 Dto*/
@Data
public class OrderMenuWeb {
    private Long menuId;
    private String name;
    private Integer orderPrice;
    private Integer count;

    public static OrderMenuWeb orderMenuFrom(OrderMenu orderMenu){
        OrderMenuWeb orderMenuWeb = new OrderMenuWeb();
        orderMenuWeb.setMenuId(orderMenu.getId());
        orderMenuWeb.setName(orderMenu.getMenu().getName());
        orderMenuWeb.setCount(orderMenu.getCount());
        orderMenuWeb.setOrderPrice(orderMenu.getOrderPrice());
        return orderMenuWeb;
    }
}
