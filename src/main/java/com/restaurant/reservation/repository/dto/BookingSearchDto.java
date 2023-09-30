package com.restaurant.reservation.repository.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data

public class BookingSearchDto {

    private Long id;
    private LocalDate date;
    private LocalTime time;
    private Long memberId; // Member
    private String name; // Member
    private String phoneNumber; // Member
    private Integer number;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime modifiedDate ;

    public BookingSearchDto() { }

    @QueryProjection
    public BookingSearchDto(Long id, LocalDate date, LocalTime time, Long memberId, String name, String phoneNumber, Integer number, LocalDateTime modifiedDate) {
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
