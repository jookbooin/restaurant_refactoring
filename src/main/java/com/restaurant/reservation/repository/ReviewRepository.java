package com.restaurant.reservation.repository;

import com.restaurant.reservation.domain.review.Review;
import com.restaurant.reservation.repository.custom.ReviewRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> , ReviewRepositoryCustom {


    @Query("Select r From Review r " +
            "Join Fetch r.member " +
            "Join fetch r.restaurant " +
            "where r.id = :rid")
    Optional<Review> findFechMember_Restaurant(@Param("rid") Long rid);


}
