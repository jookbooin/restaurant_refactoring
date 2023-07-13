package com.restaurant.reservation.repository.entityManagerRepo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class ReservationRepo {
    private final EntityManager em;

}
