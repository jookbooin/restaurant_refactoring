package com.restaurant.reservation.api.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BookingSearchApi {

    private String startDate; // 1.
    private String endDate;
    private String searchType;  // 이름 : name
    private String keyword; //  %고객%

    public BookingSearchApi() { }
}
