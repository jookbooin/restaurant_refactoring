package com.restaurant.reservation.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OneTypeData<T> {
    private T data;


}
