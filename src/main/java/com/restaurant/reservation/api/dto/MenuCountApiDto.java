package com.restaurant.reservation.api.dto;

import com.restaurant.reservation.domain.OrderMenu;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class MenuCountApiDto {
    private Long menuId;
    private Integer count;

    public static MenuCountApiDto MenuApiDtoFromOrderMenu(OrderMenu orderMenu){
        MenuCountApiDto menuCountApiDto = new MenuCountApiDto();
        menuCountApiDto.setMenuId(orderMenu.getMenu().getId());
        menuCountApiDto.setCount(orderMenu.getCount());
        return menuCountApiDto;
    }

}
