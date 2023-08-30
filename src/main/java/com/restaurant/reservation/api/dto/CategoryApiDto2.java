package com.restaurant.reservation.api.dto;

import com.restaurant.reservation.domain.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class CategoryApiDto2 {

    private Long id;
    private String name;
    private Integer level;
    private String parent;
    private List<CategoryApiDto2> children;


    @Builder
    public CategoryApiDto2(Long id, String name, Integer level, String parent, List<CategoryApiDto2> children) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.parent = parent;
        this.children = children;
    }

    /** Category  -> CategoryDto*/
    public static CategoryApiDto2 of(Category category) {
        return  CategoryApiDto2.builder()
                .id(category.getId())
                .name(category.getName())
                .parent(category.getParent() == null ? null: category.getParent().getName())
                .level(category.getLevel())
                .children(category.getChildren().stream().map(CategoryApiDto2::of).collect(Collectors.toList()))
                .build();

    }



}
