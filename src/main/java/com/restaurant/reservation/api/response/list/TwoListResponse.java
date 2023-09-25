package com.restaurant.reservation.api.response.list;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TwoListResponse<T> {
    private T list1;
    private T list2;
}
