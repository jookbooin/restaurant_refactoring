package com.restaurant.reservation.api.request.search;

import lombok.Getter;
import lombok.Setter;

/** Validation 처러 할 것*/

@Setter
@Getter
public class MenuSearchRequest {
    private String searchName;
}
