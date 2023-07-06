package com.restaurant.reservation.domain.booking;

import com.restaurant.reservation.domain.OrderMemu;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@DiscriminatorValue("reservation")
public class Reservation extends Booking {

    private LocalTime time; // 사전 예약 시간 -> enum으로 돌릴지 보류

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL)
    private List<OrderMemu> orderItems = new ArrayList<>();
}
