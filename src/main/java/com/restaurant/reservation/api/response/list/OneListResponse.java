package com.restaurant.reservation.api.response.list;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OneListResponse<T> {
    private T data;

}
