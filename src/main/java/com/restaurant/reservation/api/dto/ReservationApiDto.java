package com.restaurant.reservation.api.dto;

import com.restaurant.reservation.domain.booking.Reservation;
import com.restaurant.reservation.domain.enumType.TimeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.format.DateTimeFormatter;

@ToString
@Getter
@Setter
public class ReservationApiDto {

    private Long id;
    private int number;
    private String date;
    private String time;
    private String modifiedDate; // hidden
    private String status; // 상태 사전예약

    public static ReservationApiDto createApiDto(Reservation reservation){
        ReservationApiDto apiDto = new ReservationApiDto();
        apiDto.setId(reservation.getId());
        apiDto.setNumber(reservation.getNumber());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        apiDto.setDate(reservation.getDate().format(formatter));
        apiDto.setTime(TimeEnum.transferTimeToString(reservation.getTime()));
        apiDto.setModifiedDate(reservation.getModifiedDate().format(formatter));
        apiDto.setStatus(reservation.getStatus().getStatus());

        return apiDto;
    }
}