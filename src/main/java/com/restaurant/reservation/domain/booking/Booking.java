package com.restaurant.reservation.domain.booking;

import com.restaurant.reservation.domain.Tables;
import com.restaurant.reservation.domain.enumType.ReservationStatus;
import com.restaurant.reservation.domain.members.Member;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Entity
@Table(name = "booking")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "dtype")
public abstract class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tables_id")
    private Tables tables;          // 나중에 뺄지 결정
    private LocalDate date; // 사전 예약 날짜
    private int number; // 예약 인원
    @Column(name = "status")
    private ReservationStatus reservationStatus; // 예약완료 , pre , 노쇼
}
