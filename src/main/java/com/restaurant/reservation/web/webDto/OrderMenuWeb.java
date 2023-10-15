package com.restaurant.reservation.web.webDto;

import com.restaurant.reservation.domain.OrderMenu;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Web - Controller 까지에서만 사용되는 메뉴 관리 Dto*/
@Data
@NoArgsConstructor
public class OrderMenuWeb {
    private Long menuId;
    private String name;
    private Integer orderPrice;
    private Integer count;
    
    @Builder
    public OrderMenuWeb(Long menuId, String name, Integer orderPrice, Integer count) {
        this.menuId = menuId;
        this.name = name;
        this.orderPrice = orderPrice;
        this.count = count;
    }

    public static OrderMenuWeb webFrom(OrderMenu orderMenu){
        return OrderMenuWeb.builder()
                .menuId(orderMenu.getId())
                .name(orderMenu.getMenu().getName())
                .count(orderMenu.getCount())
                .orderPrice(orderMenu.getOrderPrice())
                .build();
    }
}
