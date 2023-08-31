package com.restaurant.reservation.api.request;

import com.restaurant.reservation.api.response.MenuCountResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@ToString
@Getter
@Setter
public class UpdateReservationRequest {

    private Long rid;
    @NotNull
    private Integer number;
    @NotBlank
    private String date;
    @NotBlank
    private String time;
    List<MenuCountResponse> orderMenuList;

    public int MenuCount(){
        return orderMenuList.stream()
                .mapToInt(o->o.getCount())
                .sum();
    }

}
