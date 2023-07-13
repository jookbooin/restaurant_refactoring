package com.restaurant.reservation.repository;

import com.restaurant.reservation.domain.OrderMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderMenuRepository extends JpaRepository<OrderMenu, Long> {

}
