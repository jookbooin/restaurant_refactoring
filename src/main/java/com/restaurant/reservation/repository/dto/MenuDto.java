package com.restaurant.reservation.repository.dto;

import com.restaurant.reservation.domain.enumType.MenuType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class MenuDto {
    private Long id;
    private String name;
    private int price;
    private String intro;
    private MenuType menuType;
}