package com.restaurant.reservation.api.dto;

import com.restaurant.reservation.domain.OrderMenu;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class  MenuApiDto {
    private Long menuId;
    private Integer count;

    public static MenuApiDto MenuApiDtoFromOrderMenu(OrderMenu orderMenu){
        MenuApiDto menuApiDto = new MenuApiDto();
        menuApiDto.setMenuId(orderMenu.getMenu().getId());
        menuApiDto.setCount(orderMenu.getCount());
        return menuApiDto;
    }

}
