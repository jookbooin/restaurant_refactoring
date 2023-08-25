package com.restaurant.reservation.repository.dto;

import com.restaurant.reservation.domain.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
public class CategoryDto {
    private Long id;
    private String branch;
    private String name;
    private Integer level;
    private String parent;
    private List<CategoryDto> children;


    public static CategoryDto of(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setBranch(categoryDto.getBranch());
        categoryDto.setName(categoryDto.getName());
        if(category.getParent() == null){
            categoryDto.setParent("root");
        }else{
            categoryDto.setParent(category.getParent().getName());
        }
        categoryDto.setLevel(category.getLevel());
        categoryDto.setChildren(category.getChildren().stream().map(CategoryDto::of).collect(Collectors.toList()));
        return categoryDto;
    }


}
