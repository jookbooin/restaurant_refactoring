package com.restaurant.reservation.repository;

import com.restaurant.reservation.domain.review.UploadFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UploadFileRepository extends JpaRepository<UploadFile,Long> {
}
