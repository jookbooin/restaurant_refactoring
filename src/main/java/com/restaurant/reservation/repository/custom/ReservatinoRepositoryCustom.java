package com.restaurant.reservation.repository.custom;

import com.restaurant.reservation.domain.booking.Reservation;
import com.restaurant.reservation.repository.dto.BookingSearch;
import com.restaurant.reservation.repository.dto.BookingSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReservatinoRepositoryCustom {

    Page<Reservation> findAllReservation(BookingSearch bookingSearch , Pageable pageable);
    Page<BookingSearchDto> findAllAdvanceReservation(BookingSearch bookingSearch , Pageable pageable);
}
