package com.restaurant.reservation.web.form;

import com.restaurant.reservation.domain.dto.OrderMenuDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@ToString
@Getter
@Setter
public class AdvanceReservationForm {
    @NotNull
    private Integer number;
    @NotBlank
    private String date;
    @NotBlank
    private String time;
    List<OrderMenuDto> orderMenuList;
}
