package com.restaurant.reservation.web.form;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ToString
@Getter
@Setter
public class AdvanceForm {

    @NotNull
    @Range(min = 1 ,max = 8)
    private Integer number;
    @NotBlank
    private String date;
    @NotBlank
    private String time;

}
