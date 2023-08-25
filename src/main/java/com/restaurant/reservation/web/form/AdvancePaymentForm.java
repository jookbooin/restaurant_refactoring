package com.restaurant.reservation.web.form;

import com.restaurant.reservation.repository.dto.OrderMenuDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@ToString
@Getter
@Setter
public class AdvancePaymentForm {

    private Integer number;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @DateTimeFormat(pattern = "a h:mm")
    private LocalTime time;
    List<OrderMenuDto> orderMenuList;
}
