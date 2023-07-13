package com.restaurant.reservation.service;

import com.restaurant.reservation.domain.Dto.MenuDto;
import com.restaurant.reservation.domain.Menu;
import com.restaurant.reservation.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
