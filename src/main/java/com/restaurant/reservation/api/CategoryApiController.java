package com.restaurant.reservation.api;

import com.restaurant.reservation.api.dto.CategoryApiDto2;
import com.restaurant.reservation.repository.CategoryRepository;
import com.restaurant.reservation.service.CategoryService;
import lombok.AllArgsConstructor;
import lombok.Data;
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
    public OneListResult findRootCategory_페이징불가(){

        List<CategoryApiDto2> apiDtoList = categoryRepository.findByParentIsNullOrderByIdAsc().stream().map(CategoryApiDto2::of).collect(Collectors.toList());
        return new OneListResult(apiDtoList);
    }

    @GetMapping("/api/category/root/2")
    public OneListResult findRootCategory_parent패치조인(){

        List<CategoryApiDto2> result = categoryRepository.findParentWithFetchJoin().stream().map(CategoryApiDto2::of).collect(Collectors.toList());
        return new OneListResult(result);
    }
    
    

    @Data
    @AllArgsConstructor
    static class OneListResult<T> {
        private T data;

    }
}
