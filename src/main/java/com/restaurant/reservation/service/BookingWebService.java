package com.restaurant.reservation.service;

import com.restaurant.reservation.repository.ReservationRepository;
import com.restaurant.reservation.repository.dto.BookingSearch;
import com.restaurant.reservation.repository.dto.BookingSearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

// Booking 화면 맞춤형 조회는  Service를 지난다.
@Service
@RequiredArgsConstructor
public class BookingWebService {

    private final ReservationRepository reservationRepository;

    public Page<BookingSearchDto> findAllAdvanceReservation(BookingSearch bookingSearch, Pageable pageable){

        Page<BookingSearchDto> result = reservationRepository.findAllAdvanceReservation(bookingSearch, pageable);
        return result;
    }


}
