package com.restaurant.reservation.domain;

import com.restaurant.reservation.domain.enumType.MenuType;

import javax.persistence.*;

@Entity
public class Menu {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Long id;
    private String name;
    private String price;
    private String intro;
    @Enumerated(EnumType.STRING)
    @Column(name = "menu_type")
    private MenuType menuType;
}
