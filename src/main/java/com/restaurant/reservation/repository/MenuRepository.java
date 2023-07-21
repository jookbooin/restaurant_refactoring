package com.restaurant.reservation.repository;

import com.restaurant.reservation.domain.Menu;
import com.restaurant.reservation.domain.enumType.MenuType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {

//  findAll :  em.createQuery("select i from Item i ", Item.class).getResultList();

    List<Menu> findByMenuType(MenuType menuType);
}
