package com.restaurant.reservation.api.response.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TwoDataResponse<T1,T2> {
    private T1 data1;
    private T2 data2;
}
