package com.restaurant.reservation.domain.booking;

import com.restaurant.reservation.domain.enumType.BookingStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@ToString
@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "dtype")
public abstract class Booking {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private Long id;

//    private LocalDateTime completeDateTime;  // 방문 완료 DateTime
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "table_id")
//    private Tables tables;

    private int number;  // 방문 인원수 : 4명

    @Enumerated(EnumType.STRING)
    private BookingStatus status; // 웨이팅 , 사전예약

    public void chageBookingStatus(BookingStatus status){
        this.status = status;
    }
}
