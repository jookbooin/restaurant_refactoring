package com.restaurant.reservation.repository;

import com.restaurant.reservation.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MenuRepositoryTest {

    @Autowired
    MenuRepository menuRepository;
    @Autowired
    MenuService menuService;
   }