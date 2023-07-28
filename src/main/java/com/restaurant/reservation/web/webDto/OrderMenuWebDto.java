package com.restaurant.reservation.web.webDto;

import lombok.Data;

/** Web - Controller 까지에서만 사용되는 메뉴 관리 Dto*/
@Data
public class OrderMenuWebDto {
    private Long menuId;
    private String name;
    private Integer price;

    private Integer count;
}
