package com.restaurant.reservation.web.webDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class BookingSearchWeb {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate; // 1.
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    private String searchType;  // 이름 : name
    private String keyword; //  %고객%


}
