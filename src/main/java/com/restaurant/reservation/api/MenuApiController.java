package com.restaurant.reservation.api;

import com.restaurant.reservation.api.dto.Form.MenuSaveRequest;
import com.restaurant.reservation.api.dto.Form.MenuUpdateRequest;
import com.restaurant.reservation.api.dto.MenuSearchApiDto;
import com.restaurant.reservation.api.dto.searchApi.MenuSearchApi;
import com.restaurant.reservation.domain.CategoryMenu;
import com.restaurant.reservation.repository.CategoryRepository;
import com.restaurant.reservation.repository.dto.MenuDto;
import com.restaurant.reservation.service.CategoryMenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

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
        if (StringUtils.hasText(cateName)) {

            try {
                cateCode = categoryRepository.findCodeByName(cateName).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리 명입니다"));
            } catch (IllegalArgumentException e) {
                log.info("IllegalArgumentException 발생");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

        }else {
//            throw new IllegalArgumentException("검색 조건이 없습니다.");
            log.info("검색 조건이 없습니다.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        log.info("cateCode : {} ",cateCode);
        List<CategoryMenu> categoryMenuList = categoryMenuService.findCategoryMenu(cateCode);

        MenuSearchApiDto result = MenuSearchApiDto.of(cateName, cateCode, categoryMenuList);

        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @PostMapping("/api/menu/save")
    public ResponseEntity<?> menuSave(@RequestBody MenuSaveRequest menuSaveRequest){

        log.info("menuSaveRequest : {}",menuSaveRequest);
        /** 에러가 있다면
         *  return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
         * */
        MenuDto menuDto = MenuDto.builder().name(menuSaveRequest.getMenuName())
                .price(menuSaveRequest.getMenuPrice())
                .description(menuSaveRequest.getDescription()).build();

        try {
            categoryMenuService.save(menuSaveRequest.getCategoryName(),menuDto );
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/api/menu/{id}/update")
    public ResponseEntity<?> menuUpdate (@PathVariable("id") Long id ,@RequestBody MenuUpdateRequest MenuUpdateRequest){

        log.info("id : {} ,  MenuUpdateRequest : {}",id,MenuUpdateRequest);

        MenuDto menuDto = MenuDto.builder().id(id)
                .name(MenuUpdateRequest.getMenuName())
                .price(MenuUpdateRequest.getMenuPrice())
                .description(MenuUpdateRequest.getDescription()).build();

        try {
            categoryMenuService.update(MenuUpdateRequest.getCategoryName(),menuDto);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/api/menu/{id}/delete")
    public ResponseEntity<?> menuDelete (@RequestParam("id") Long id){

        try {
            categoryMenuService.delete(id);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
