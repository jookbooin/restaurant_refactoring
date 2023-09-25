package com.restaurant.reservation.repository.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.restaurant.reservation.repository.dto.ReviewSearch;
import com.restaurant.reservation.repository.dto.ReviewSearchDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;

@Slf4j
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public ReviewRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<ReviewSearchDto> findAllReview(ReviewSearch reviewSearch, Pageable pageable) {
        return null;
    }
}
