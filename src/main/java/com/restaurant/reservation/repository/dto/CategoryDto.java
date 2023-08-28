package com.restaurant.reservation.repository.dto;

import com.restaurant.reservation.domain.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@NoArgsConstructor
public class CategoryDto {
    private Long id;
    private String name;
    private Integer level;
    private String parent;
    private List<CategoryDto> children;

    @Builder
    public CategoryDto(Long id, String name, Integer level, String parent, List<CategoryDto> children) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.parent = parent;
        this.children = children;

    }


    /** Category  -> CategoryDto*/
    public static CategoryDto of(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(categoryDto.getName());
        if(category.getParent() != null){
            categoryDto.setParent(category.getParent().getName());
        }
        categoryDto.setLevel(category.getLevel());
        categoryDto.setChildren(category.getChildren().stream().map(CategoryDto::of).collect(Collectors.toList()));
        return categoryDto;
    }


}
