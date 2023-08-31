package com.restaurant.reservation.domain;

import com.restaurant.reservation.repository.dto.MenuDto;
import com.restaurant.reservation.domain.enumType.MenuType;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

@ToString
@Getter
@Entity
public class Menu {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Long id;
    private String name;
    private int price;
    private String description;
    @Enumerated(EnumType.STRING)
    @Column(name = "menu_type")
    private MenuType menuType;

    public Menu(){
    }

    public Menu(String name) {
        this.name = name;
    }

    @Builder
    public Menu(String name, int price, String description, MenuType menuType) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.menuType = menuType;
    }

    public void updateName(String name){
        this.name = name;
    }
    public void updatePrice(Integer price){
        this.price = price;
    }
    public  void updateDescription(String description){
        this.description = description;
    }

    public static Menu createMenu(MenuDto menuDto){

        return Menu.builder()
                .name(menuDto.getName())
                .price(menuDto.getPrice())
                .description(menuDto.getDescription())
                .menuType(menuDto.getMenuType())
                .build();
//        return new Menu(menuDto.getName(), menuDto.getPrice(), menuDto.getDescription(), menuDto.getMenuType());
    }

}
