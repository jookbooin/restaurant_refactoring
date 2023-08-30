package com.restaurant.reservation.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor()
public class CategoryApiDto {

    private String code;
    private String categoryName;
    private Integer level;

}
