package com.restaurant.reservation.repository;

import com.restaurant.reservation.domain.booking.Waiting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WaitingRepository extends JpaRepository<Waiting,Long> {
}
