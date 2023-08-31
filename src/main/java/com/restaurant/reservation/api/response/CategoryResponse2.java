package com.restaurant.reservation.api.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor()
public class CategoryResponse2 {

    private String code;
    private String categoryName;
    private Integer level;

}
