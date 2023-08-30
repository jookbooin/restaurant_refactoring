package com.restaurant.reservation.service;

import com.restaurant.reservation.domain.CategoryMenu;
import com.restaurant.reservation.repository.CategoryMenuRepository;
import com.restaurant.reservation.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryMenuService {

    private final CategoryRepository categoryRepository;
    private final CategoryMenuRepository categoryMenuRepository;


    public List<CategoryMenu> findCategoryMenu(String cateCode){

        List<CategoryMenu> categoryMenuList = categoryMenuRepository.findAllContainCode(cateCode);

        return categoryMenuList;

    }
}
