package com.restaurant.reservation.service;

import com.restaurant.reservation.domain.dto.MenuDto;
import com.restaurant.reservation.domain.Menu;
import com.restaurant.reservation.domain.enumType.MenuType;
import com.restaurant.reservation.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MenuService  {

    private final MenuRepository menuRepository;

    public void deleteMenu(Long id) {

        Optional<Menu> menuClass = menuRepository.findById(id);
        if (menuClass.isPresent()) {
            menuRepository.delete(menuClass.get());
        } else {
            throw new RuntimeException("request Menu id is not found " + id);
        }
    }

    // RAW menu usage 사용하면 안되고 DTO를 통해 개선 필요함
    public Menu findOne(Long id) {
         return menuRepository.findById(id).orElseThrow(() -> new RuntimeException("can't find Menu"));
    }

    public List<Menu> findAllMenu() {
        return menuRepository.findAll();
    }

    public Menu addMenu(MenuDto menuDto){
        Menu menu = Menu.createMenu(menuDto);
        return menuRepository.save(menu);
    }

    public List<MenuDto> findSpecialMenu(){
        List<Menu> specialList = menuRepository.findByMenuType(MenuType.SPECIAL);
        List<MenuDto> specialDtoList = MenuToMenuDto(specialList);
        return specialDtoList;
    }

    private static List<MenuDto> MenuToMenuDto(List<Menu> specialList) {
        List<MenuDto> specialDtoList = new ArrayList<>();
        specialList.forEach(o -> {
            MenuDto menuDto = new MenuDto();
            menuDto.setId(o.getId());
            menuDto.setPrice(o.getPrice());
            menuDto.setIntro(o.getIntro());
            menuDto.setName(o.getName());
            menuDto.setMenuType(o.getMenuType());
            specialDtoList.add(menuDto);
        });
        return specialDtoList;
    }

}
