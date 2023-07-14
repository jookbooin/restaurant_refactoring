package com.restaurant.reservation.domain.booking;

import com.restaurant.reservation.domain.OrderMenu;
import com.restaurant.reservation.domain.members.Member;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@DiscriminatorValue("reservation")
@EntityListeners(AuditingEntityListener.class)
public class Reservation extends Booking {

    private LocalTime time; // 사전 예약 시간 -> enum으로 돌릴지 보류

    @LastModifiedDate
    private LocalDate date;  /** 예약 시점 + 예약 변경 시점  <-> 위약금 비교하기위해서 필요*/


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL)
    private List<OrderMenu> orderMenus = new ArrayList<>();



    public Reservation(LocalTime time) {
        this.time = time;
    }

    public Reservation() {}

    public Reservation( Member member,LocalTime time) {
        this.time = time;
        this.member = member;
    }

    public static Reservation createReservation(Member member, LocalDate date, int number, LocalTime time, List<OrderMenu> orderMenuList){
        Reservation reservation = new Reservation(member,time);
        reservation.setDate(date);
        reservation.setNumber(number);
        for (OrderMenu orderMenu : orderMenuList) {
            reservation.addOrderMenu(orderMenu);
        }

        return reservation;

    }

    public void addOrderMenu(OrderMenu orderMenu) {
        orderMenus.add(orderMenu);
        orderMenu.setReservation(this);
    }


}
