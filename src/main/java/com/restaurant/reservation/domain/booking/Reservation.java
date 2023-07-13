package com.restaurant.reservation.domain.booking;

import com.restaurant.reservation.domain.OrderMenu;
import com.restaurant.reservation.domain.members.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@DiscriminatorValue("reservation")
public class Reservation extends Booking {

    private LocalTime time; // 사전 예약 시간 -> enum으로 돌릴지 보류

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL)
    private List<OrderMenu> orderItems = new ArrayList<>();

    public Reservation(LocalTime time) {
        this.time = time;
    }

    public Reservation() {}

}
