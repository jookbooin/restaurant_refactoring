package com.restaurant.reservation.api.dto.Form;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class MenuUpdateRequest {

    private String categoryName;
    private String menuName;
    private Integer menuPrice;
    private String description;

}
