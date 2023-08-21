package com.restaurant.reservation.repository.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data

public class BookingSearchDto {

    private Long id;
    private LocalDate date;
    private LocalTime time;
    private Long memberId;
    private String name;
    private String phoneNumber;
    private Integer number;


    private LocalDate modifiedDate ;

    public BookingSearchDto() { }

    @QueryProjection

    public BookingSearchDto(Long id, LocalDate date, LocalTime time, Long memberId, String name, String phoneNumber, Integer number, LocalDate modifiedDate) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.memberId = memberId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.number = number;
        this.modifiedDate = modifiedDate;
    }
}
