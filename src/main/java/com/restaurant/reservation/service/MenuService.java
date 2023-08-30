package com.restaurant.reservation.service;

import com.restaurant.reservation.domain.Menu;
import com.restaurant.reservation.domain.enumType.MenuType;
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
            menuDto.setDescription(o.getDescription());
            menuDto.setName(o.getName());
            menuDto.setMenuType(o.getMenuType());
            specialDtoList.add(menuDto);
        });
        return specialDtoList;
    }

}
