package com.restaurant.reservation.domain.booking;

import com.restaurant.reservation.domain.enumType.BookingStatus;
import com.restaurant.reservation.domain.members.Member;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "dtype")
@EntityListeners(AuditingEntityListener.class)
public abstract class Booking {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private Long id;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "table_id")
//    private Tables tables;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    private int number;  // 방문 인원수 : 4명
    @CreatedDate
    private LocalDateTime createdDate; // 예약 생성 시점
    //    private LocalDateTime completeDateTime;  // 방문 완료 DateTime
    @Enumerated(EnumType.STRING)
    private BookingStatus status; // 웨이팅 , 사전예약

    public void chageBookingStatus(BookingStatus status){
        this.status = status;
    }

}
