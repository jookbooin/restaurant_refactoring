package com.restaurant.reservation.repository;

import com.restaurant.reservation.domain.review.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface fileRepository extends JpaRepository<File,Long> {
}
