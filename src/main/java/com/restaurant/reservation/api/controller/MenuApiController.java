package com.restaurant.reservation.api.controller;

import com.restaurant.reservation.api.request.search.MenuSearchRequest;
import com.restaurant.reservation.api.response.MenuSearchResponse;
import com.restaurant.reservation.api.response.MessageResponse;
import com.restaurant.reservation.domain.CategoryMenu;
import com.restaurant.reservation.common.exception.domain.CategoryException;
import com.restaurant.reservation.repository.CategoryRepository;
import com.restaurant.reservation.repository.dto.MenuDto;
import com.restaurant.reservation.service.CategoryMenuService;
import com.restaurant.reservation.web.form.MenuSaveForm;
import com.restaurant.reservation.web.form.MenuUpdateForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MenuApiController {

    private final CategoryMenuService categoryMenuService;
    private final CategoryRepository categoryRepository;

    @GetMapping("/api/menu/search")
    public ResponseEntity<?> MenuSearch (@ModelAttribute MenuSearchRequest condition){

        String cateName = condition.getSearchName();
        log.info(" searchName : [{}]", cateName );
        String cateCode = null;
        if (StringUtils.hasText(cateName)) {
                cateCode = categoryRepository.findCodeByName(cateName).orElseThrow(() -> new CategoryException("존재하지 않는 카테고리 명입니다"));
        }else {
                throw new CategoryException("카테고리명이 정확한 형식이 아닙니다.");

        }

        log.info("cateCode : {} ",cateCode);
        List<CategoryMenu> categoryMenuList = categoryMenuService.findCategoryMenu(cateCode);

        MenuSearchResponse response = MenuSearchResponse.of(cateName, cateCode, categoryMenuList);

        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/api/menu/search/page")
    public ResponseEntity<?> MenuSearchPage (@ModelAttribute MenuSearchRequest condition , @PageableDefault(page = 0, size = 6) Pageable pageable){

        String cateName = condition.getSearchName();
        log.info(" searchName : [{}]", cateName );
        log.info(" page : [{}] , size : [{}]",pageable.getPageNumber(),pageable.getPageSize());

        String cateCode = null;
        if (StringUtils.hasText(cateName)) {
            cateCode = categoryRepository.findCodeByName(cateName).orElseThrow(() -> new CategoryException("존재하지 않는 카테고리 명입니다"));
        }else {
            throw new CategoryException("카테고리명이 정확한 형식이 아닙니다.");
        }

        log.info("cateCode : {} ",cateCode);

        List<CategoryMenu> categoryMenuList = categoryMenuService.findCategoryMenu(cateCode, pageable);

        /** 페이징 적용 x */
        MenuSearchResponse response = MenuSearchResponse.of(cateName, cateCode, categoryMenuList);

        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/api/menu/save")
    public ResponseEntity<?> menuSave(@Validated @RequestBody MenuSaveForm request){

        log.info("menuSaveRequest : {}",request);
        /** 에러가 있다면
         *  return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
         * */
        MenuDto menuDto = MenuDto.builder().name(request.getMenuName())
                .price(request.getMenuPrice())
                .description(request.getDescription()).build();

        categoryMenuService.save(request.getCategoryName(),menuDto );

        return new ResponseEntity<>(new MessageResponse("메뉴 등록했습니다."),HttpStatus.OK);
    }

    @PatchMapping("/api/menu/{id}/update")
    public ResponseEntity<?> menuUpdate (@PathVariable("id") Long id ,@Validated @RequestBody MenuUpdateForm request){

        log.info("id : {} ,  MenuUpdateRequest : {}",id,request);

        MenuDto menuDto = MenuDto.builder().id(id)
                .name(request.getMenuName())
                .price(request.getMenuPrice())
                .description(request.getDescription()).build();

        categoryMenuService.update(request.getCategoryName(),menuDto);

        return new ResponseEntity<>(new MessageResponse("메뉴 수정했습니다."),HttpStatus.OK);
    }

    @DeleteMapping("/api/menu/{id}/delete")
    public ResponseEntity<?> menuDelete (@PathVariable("id") Long id){

        categoryMenuService.delete(id);

        return new ResponseEntity<>(new MessageResponse("메뉴 삭제했습니다."),HttpStatus.OK);
    }

}
