package com.restaurant.reservation.web.form;

import lombok.Data;
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
    List<advanceFormDto> orderMenuList;


    @Data
    public static class advanceFormDto{
        private Long menuId;
        private String name;
        private Integer price;

        private Integer count;
    }

}
