package com.restaurant.reservation.api.controller;

import com.restaurant.reservation.api.response.CategoryResponse;
import com.restaurant.reservation.api.response.list.OneListResponse;
import com.restaurant.reservation.repository.CategoryRepository;
import com.restaurant.reservation.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CategoryApiController {
    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;


    @GetMapping("/api/category/root/1")
    public OneListResponse findRootCategory_페이징불가(){

        List<CategoryResponse> responseList = categoryRepository.findByParentIsNullOrderByIdAsc().stream().map(CategoryResponse::of).collect(Collectors.toList());
        return new OneListResponse(responseList);
    }

    @GetMapping("/api/category/root/2")
    public OneListResponse findRootCategory_parent패치조인(){

        List<CategoryResponse> responseList = categoryRepository.findParentWithFetchJoin().stream().map(CategoryResponse::of).collect(Collectors.toList());
        return new OneListResponse(responseList);
    }

}
