package com.restaurant.reservation.api.response;

import com.restaurant.reservation.domain.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class CategoryResponse {

    private Long id;
    private String name;
    private Integer level;
    private String parent;
    private List<CategoryResponse> children;


    @Builder
    public CategoryResponse(Long id, String name, Integer level, String parent, List<CategoryResponse> children) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.parent = parent;
        this.children = children;
    }

    /** Category  -> CategoryDto*/
    public static CategoryResponse responseFrom(Category category) {
        return  CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .parent(category.getParent() == null ? null: category.getParent().getName())
                .level(category.getLevel())
                .children(category.getChildren().stream().map(CategoryResponse::responseFrom).collect(Collectors.toList()))
                .build();

    }



}
