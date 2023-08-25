package com.restaurant.reservation.repository.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
public class AdminBookingDto { // Admin 화면에서 예약 조회
    private Long id;
    private LocalDate date;
    private LocalTime time;
    private String name;
    private Integer number;
    private LocalDateTime bookingDateTime;
}
