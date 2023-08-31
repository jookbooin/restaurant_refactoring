package com.restaurant.reservation.api.dto.Form;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class MenuSaveRequest {
    private String categoryName;
    private String menuName;
    private Integer menuPrice;
    private String description;

}
