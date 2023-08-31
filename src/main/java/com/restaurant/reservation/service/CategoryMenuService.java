package com.restaurant.reservation.service;

import com.restaurant.reservation.domain.Category;
import com.restaurant.reservation.domain.CategoryMenu;
import com.restaurant.reservation.domain.Menu;
import com.restaurant.reservation.repository.CategoryMenuRepository;
import com.restaurant.reservation.repository.CategoryRepository;
import com.restaurant.reservation.repository.MenuRepository;
import com.restaurant.reservation.repository.dto.MenuDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryMenuService {

    private final CategoryRepository categoryRepository;
    private final CategoryMenuRepository categoryMenuRepository;
    private final MenuRepository menuRepository;


    public List<CategoryMenu> findCategoryMenu(String cateCode){

        List<CategoryMenu> categoryMenuList = categoryMenuRepository.findAllContainCode(cateCode);

        return categoryMenuList;

    }

    @Transactional
    public CategoryMenu  save(String categoryName , MenuDto menuDto){
        Category category = categoryRepository.findByName(categoryName).orElseThrow(() -> new IllegalArgumentException("카테고리명이 존재하지 않습니다."));
        Menu menu = menuRepository.save(Menu.createMenu(menuDto));
        CategoryMenu categoryMenu = CategoryMenu.createCategoryMenu(menu, category);
        return categoryMenuRepository.save(categoryMenu);

    }

    @Transactional
    public void update(String categoryName,MenuDto menuDto) {

        /**
         * 1.카테고리가 변경되는 경우 -> 카테고리 코드 , level 변경됨.
         * */
        Category findCategory = categoryRepository.findByName(categoryName).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리 이름입니다."));

        CategoryMenu findCategoryMenu = categoryMenuRepository.findByMenuId(menuDto.getId()).orElseThrow(() -> new IllegalArgumentException("해당 메뉴ID를 가지고있는 CategoryMenu가 존재하지 않습니다."));

        findCategoryMenu.updateMenu(findCategory,menuDto);

    }

    @Transactional
    public void delete(Long id) {
        CategoryMenu findCategoryMenu = categoryMenuRepository.findByMenuId(id).orElseThrow(() -> new IllegalArgumentException("해당 메뉴ID를 가지고있는 CategoryMenu가 존재하지 않습니다."));
        categoryMenuRepository.delete(findCategoryMenu);
        menuRepository.deleteById(id);
    }
}
