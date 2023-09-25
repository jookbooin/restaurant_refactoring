package com.restaurant.reservation.web.form;

import com.restaurant.reservation.web.webDto.OrderMenuWeb;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@ToString
@Getter
@Setter
public class AdvanceReservationForm {
    @NotNull
    @Min(1)
    private Integer number;
    @NotBlank
    private String date;
    @NotBlank
    private String time;
    List<OrderMenuWeb> orderMenuList;


//    @Data
//    public static class advanceFormDto{
//        private Long menuId;
//        private String name;
//        private Integer price;
//
//        private Integer count;
//    }

}
