package com.restaurant.reservation.api.response;

import com.restaurant.reservation.domain.OrderMenu;
import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderMenuResponse {
    private Long menuId;
    private String name;
    private Integer orderPrice;
    private Integer count;

    @Builder
    public OrderMenuResponse(Long menuId, String name, Integer orderPrice, Integer count) {
        this.menuId = menuId;
        this.name = name;
        this.orderPrice = orderPrice;
        this.count = count;
    }

    public static OrderMenuResponse responseFrom(OrderMenu orderMenu){
        return OrderMenuResponse.builder()
                .menuId(orderMenu.getId())
                .name(orderMenu.getMenu().getName())
                .count(orderMenu.getCount())
                .orderPrice(orderMenu.getOrderPrice())
                .build();

    }

    public int getTotalPrice(){
        return this.getOrderPrice()*this.getCount();
    }
}
