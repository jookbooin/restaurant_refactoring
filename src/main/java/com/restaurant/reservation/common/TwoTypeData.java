package com.restaurant.reservation.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TwoTypeData<T1,T2> {
    private T1 data1;
    private T2 data2;
}
