package com.restaurant.reservation.domain.booking;

import com.restaurant.reservation.domain.Tables;
import com.restaurant.reservation.domain.enumType.BookingStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "dtype")
public abstract class Booking {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "table_id")
    private Tables tables;
    private LocalDate date;

    private int number;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;
}
