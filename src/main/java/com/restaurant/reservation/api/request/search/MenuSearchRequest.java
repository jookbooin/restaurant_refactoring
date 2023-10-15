package com.restaurant.reservation.api.request.search;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Validation 처러 할 것*/

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MenuSearchRequest {
    private String searchName;
}
