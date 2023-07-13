package com.restaurant.reservation.repository;

import com.restaurant.reservation.domain.Tables;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TableRepository extends JpaRepository<Tables,Long> {

}
