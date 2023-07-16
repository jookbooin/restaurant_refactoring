package com.restaurant.reservation.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@Builder
public class ReservationDto {
    private int number;
    private LocalDate date;
    private LocalTime time;

    private List<OrderMenuDto> orderMenuDtoList;

}
