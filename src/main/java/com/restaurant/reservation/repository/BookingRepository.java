package com.restaurant.reservation.repository;

import com.restaurant.reservation.domain.booking.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking,Long> {

}
