package com.restaurant.reservation.web.webDto;

import com.restaurant.reservation.domain.booking.Reservation;
import com.restaurant.reservation.domain.enumType.TimeEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;
import java.util.List;

/** 사용자 MyPage에 예약 목록을 전달하는 Dto */
@Setter
@Getter
public class BookingWebDto {

//    private String restaurantName;
    private Long id;
    private int number;
    private String date;
    private String time;

    private List<OrderMenuWebDto> orderMenuList;

    public static BookingWebDto createDto(Reservation reservation){
        BookingWebDto webDto = new BookingWebDto();
        webDto.setId(reservation.getId());
        webDto.setNumber(reservation.getNumber());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        webDto.setDate(reservation.getDate().format(formatter));
        webDto.setTime(TimeEnum.transferTimeToString(reservation.getTime()));
        return webDto;
    }
}
