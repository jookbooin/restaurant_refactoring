package com.restaurant.reservation.api.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.restaurant.reservation.domain.booking.Reservation;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@ToString
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationResponse {

    private Long id;
    private int number;
    @JsonFormat(pattern = "yyyy-MM-dd ")
    private LocalDate date;
    @JsonFormat(pattern="HH:mm")
    private LocalTime time;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime modifiedDate; // hidden
    private String status; // 상태 사전예약

    @Builder
    public ReservationResponse(Long id, int number, LocalDate date, LocalTime time, LocalDateTime modifiedDate, String status) {
        this.id = id;
        this.number = number;
        this.date = date;
        this.time = time;
        this.modifiedDate = modifiedDate;
        this.status = status;
    }

    public static ReservationResponse responseFrom(Reservation reservation){

        return ReservationResponse.builder()
                .id(reservation.getId())
                .number(reservation.getNumber())
                .date(reservation.getDate())
                .time(reservation.getTime())
                .modifiedDate(reservation.getModifiedDate())
                .status(reservation.getStatus().getStatus())
                .build();

//        ReservationResponse apiDto = new ReservationResponse();
//        apiDto.setId(reservation.getId());
//        apiDto.setNumber(reservation.getNumber());
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        apiDto.setDate(reservation.getDate().format(formatter));
//        apiDto.setTime(TimeEnum.transferTimeToString(reservation.getTime()));
//        apiDto.setModifiedDate(reservation.getModifiedDate().format(formatter));
//        apiDto.setStatus(reservation.getStatus().getStatus());
//
//        return apiDto;
    }
}
