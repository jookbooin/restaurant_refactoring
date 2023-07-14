package com.restaurant.reservation.domain.dto;

import java.time.LocalTime;
import java.util.List;

public class ReservationDto {

    private LocalTime time;

    private List<OrderMenuDto> orderMenuDtoList;

}
