package com.restaurant.reservation.repository;

import com.restaurant.reservation.domain.CategoryMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CategoryMenuRepository extends JpaRepository<CategoryMenu, Long> {

}
