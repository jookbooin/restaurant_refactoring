package com.restaurant.reservation.domain.booking;

import com.restaurant.reservation.domain.OrderMenu;
import com.restaurant.reservation.domain.enumType.BookingStatus;
import com.restaurant.reservation.domain.members.Member;
import com.restaurant.reservation.repository.dto.ReservationDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@ToString
@Setter
@Getter
@Entity
@DiscriminatorValue("예약")
public class Reservation extends Booking {
    private LocalDate date; //방문 예정 날짜 2023-09-11
    private LocalTime time; //방문 예정 시간 11:00 , 12:00

    @LastModifiedDate
    private LocalDateTime modifiedDate;  /** 예약 시점 + 예약 변경 시점 날짜  <-> 위약금 비교하기위해서 필요*/

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL , orphanRemoval = true)
    private List<OrderMenu> orderMenus = new ArrayList<>();


    public Reservation(LocalTime time) {
        this.time = time;
    }

    public Reservation() {}

    public Reservation( Member member,LocalTime time) {
        this.time = time;
        super.setMember(member);
    }

    public static Reservation createReservation(Member member, ReservationDto reservationDto, List<OrderMenu> orderMenuList){
        Reservation reservation = new Reservation(member,reservationDto.getTime());
        reservation.setDate(reservationDto.getDate());
        reservation.setNumber(reservationDto.getNumber());

        if (orderMenuList != null)
            for (OrderMenu orderMenu : orderMenuList) {
                reservation.addOrderMenu(orderMenu);
            }

        reservation.setStatus(BookingStatus.ADVANCE);
        return reservation;

    }

    public void addOrderMenu(OrderMenu orderMenu) {
        orderMenus.add(orderMenu);
        orderMenu.setReservation(this);
    }

    public void updateReservation(ReservationDto reservationDto){
        this.date =reservationDto.getDate();
        this.time = reservationDto.getTime();
//        super.setDate(reservationDto.getDate());
        super.setNumber(reservationDto.getNumber());
    }




}
