package com.restaurant.reservation.repository;

import com.restaurant.reservation.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {

//  findAll :  em.createQuery("select i from Item i ", Item.class).getResultList();
}
