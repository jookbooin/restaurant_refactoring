package com.restaurant.reservation.api;

import com.restaurant.reservation.api.dto.MenuSearchApiDto;
import com.restaurant.reservation.api.dto.searchApi.MenuSearchApi;
import com.restaurant.reservation.domain.CategoryMenu;
import com.restaurant.reservation.repository.CategoryRepository;
import com.restaurant.reservation.service.CategoryMenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MenuApiController {

    private final CategoryMenuService categoryMenuService;
    private final CategoryRepository categoryRepository;

    @GetMapping("/api/menu/search")
    public ResponseEntity<?> MenuSearch (@ModelAttribute MenuSearchApi menuSearchApi){

        String cateName = menuSearchApi.getSearchName().trim();
        log.info(" searchName : [{}]", cateName );
        String cateCode = null;
        try {
            cateCode = categoryRepository.findCodeByName(cateName).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리 명입니다"));
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.info("cateCode : {} ",cateCode);
        List<CategoryMenu> categoryMenuList = categoryMenuService.findCategoryMenu(cateCode);

        MenuSearchApiDto result = MenuSearchApiDto.of(cateName, cateCode, categoryMenuList);

        return new ResponseEntity<>(result,HttpStatus.OK);
    }
}
