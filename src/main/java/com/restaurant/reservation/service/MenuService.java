package com.restaurant.reservation.service;

import com.restaurant.reservation.domain.Menu;
import com.restaurant.reservation.repository.MenuRepository;
import com.restaurant.reservation.repository.dto.MenuDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MenuService  {

    private final MenuRepository menuRepository;

    final String categoryName ="스페셜";

    @Transactional
    public void deleteMenu(Long id) {

        Optional<Menu> menuClass = menuRepository.findById(id);
        if (menuClass.isPresent()) {
            menuRepository.delete(menuClass.get());
        } else {
            throw new RuntimeException("request Menu id is not found " + id);
        }
    }

    @Transactional
    public Menu save(MenuDto menuDto){
        Menu menu = Menu.createMenu(menuDto);
        return menuRepository.save(menu);
    }

    // RAW menu usage 사용하면 안되고 DTO를 통해 개선 필요함
    public Menu findOne(Long id) {
         return menuRepository.findById(id).orElseThrow(() -> new RuntimeException("can't find Menu"));
    }

    public List<Menu> findAllMenu() {
        return menuRepository.findAll();
    }


    private static List<MenuDto> toDto(List<Menu> menuList) {
        List<MenuDto> dtoList = new ArrayList<>();
        menuList.forEach(o -> {
                                  MenuDto menuDto = MenuDto.builder()
                                        .id(o.getId())
                                        .price(o.getPrice())
                                        .description(o.getDescription())
                                        .name(o.getName())
                                        .build();
            dtoList.add(menuDto);
        });
        return dtoList;
    }

}
