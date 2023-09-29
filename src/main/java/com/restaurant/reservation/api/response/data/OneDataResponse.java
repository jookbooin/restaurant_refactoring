package com.restaurant.reservation.api.response.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OneDataResponse<T> {
    private T data;


}
