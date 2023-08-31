package com.restaurant.reservation.api.response;

import com.restaurant.reservation.domain.OrderMenu;
import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MenuCountResponse {
    private Long menuId;
    private Integer count;

    @Builder
    public MenuCountResponse(Long menuId, Integer count) {
        this.menuId = menuId;
        this.count = count;
    }

    public static MenuCountResponse of(OrderMenu orderMenu){
        return MenuCountResponse.builder().menuId(orderMenu.getMenu().getId()).count(orderMenu.getCount()).build();

    }

}
