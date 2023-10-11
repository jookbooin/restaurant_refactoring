package com.restaurant.reservation.repository.custom;

import com.restaurant.reservation.repository.dto.ReviewSearch;
import com.restaurant.reservation.repository.dto.ReviewSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewRepositoryCustom {

    Page<ReviewSearchDto> findAllRestaurantReview(Long rid,ReviewSearch reviewSearch, Pageable pageable);
    Page<ReviewSearchDto> findAllRestaurantReview2(Long rid,ReviewSearch reviewSearch, Pageable pageable);
}
