package com.restaurant.reservation.repository;

import com.restaurant.reservation.domain.Menu;
import com.restaurant.reservation.domain.dto.MenuDto;
import com.restaurant.reservation.domain.enumType.MenuType;
import com.restaurant.reservation.service.MenuService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MenuRepositoryTest {

    @Autowired
    MenuRepository menuRepository;
    @Autowired
    MenuService menuService;
    @Test
    public void 메뉴타입_찾기() throws Exception{

        // given
        List<Menu> specialList = menuRepository.findByMenuType(MenuType.SPECIAL);
//        Assertions.assertThat(
//        )

        Assertions.assertEquals(specialList.size(),3);
        specialList.forEach(System.out::println);

        List<MenuDto> specialDtoList = menuService.findSpecialMenu();

        Assertions.assertEquals(specialDtoList.get(0).getClass(),MenuDto.class);
    }
}