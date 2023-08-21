package com.restaurant.reservation.web.webDto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
public class AdminBookingWebDto {
    private Long id;
    private LocalDate date;
    private LocalTime time;
    private String name;
    private Integer number;
    private LocalDateTime bookingDateTime;
}
