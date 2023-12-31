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
    private String code;
    private List<CategoryDto> children;

    @Builder
    public CategoryDto(Long id, String name, Integer level, String parent, String code, List<CategoryDto> children) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.parent = parent;
        this.code = code;
        this.children = children;
    }

    @Builder


    /** Category  -> CategoryDto*/
    public static CategoryDto of(Category category) {
        return  CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .parent(category.getParent() == null ? null: category.getParent().getName())
                .level(category.getLevel())
                .code(category.getCode())
                .children(category.getChildren().stream().map(CategoryDto::of).collect(Collectors.toList()))
                .build();

    }


}
