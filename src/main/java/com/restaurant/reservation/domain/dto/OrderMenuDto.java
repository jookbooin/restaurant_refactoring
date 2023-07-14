package com.restaurant.reservation.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OrderMenuDto {

    // form -> id를 통해서 list로 만들어 질 것임
    private Long menuId;
    private int count;

}
