package com.restaurant.reservation.repository.dto;

import com.restaurant.reservation.domain.enumType.MenuType;
import lombok.*;

@ToString
@Setter
@Getter
@NoArgsConstructor()
public class MenuDto {
    private Long id;
    private String name;
    private int price;
    private String intro;
    private MenuType menuType;

    @Builder
    public MenuDto(Long id, String name, int price, String intro, MenuType menuType) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.intro = intro;
        this.menuType = menuType;
    }
}
