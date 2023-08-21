package com.restaurant.reservation.domain.dto;

import com.restaurant.reservation.api.request.UpdateReservationRequest;
import com.restaurant.reservation.domain.enumType.TimeEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@ToString
@Getter
@Builder
public class ReservationDto {
    private Long rid;
    private int number;
    private LocalDate date;
    private LocalTime time;

    private List<OrderMenuDto> orderMenuList ;

    public static ReservationDto requestToDto(UpdateReservationRequest request){

        List<OrderMenuDto> orderMenuDtoList = request.getOrderMenuList().stream()
                .map(o -> new OrderMenuDto(o))
                .collect(Collectors.toList());

        return ReservationDto.builder()
                .rid(request.getRid())
                .number(request.getNumber())
                .date(LocalDate.parse(request.getDate()))
                .time(TimeEnum.transferStringToTime(request.getTime()))
                .orderMenuList(orderMenuDtoList)
                .build();
    }


}
