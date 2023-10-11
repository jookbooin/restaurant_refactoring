package com.restaurant.reservation.repository.custom;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.restaurant.reservation.domain.review.Review;
import com.restaurant.reservation.repository.dto.QReviewSearchDto;
import com.restaurant.reservation.repository.dto.ReviewSearch;
import com.restaurant.reservation.repository.dto.ReviewSearchDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

import static com.restaurant.reservation.domain.QRestaurant.restaurant;
import static com.restaurant.reservation.domain.members.QMember.member;
import static com.restaurant.reservation.domain.review.QReview.review;

@Slf4j
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public ReviewRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<ReviewSearchDto> findAllRestaurantReview2(Long rid ,ReviewSearch reviewSearch, Pageable pageable) {
        log.info("reviewSearch : {}",reviewSearch);
        List<ReviewSearchDto> content = queryFactory.select(new QReviewSearchDto(review.id,
                        review.content,
                        review.grade,
                        review.createdDate,
                        restaurant.id,
                        member.id,
                        member.memberInfo.name))
                .from(review)
                .leftJoin(review.member, member)
                .leftJoin(review.restaurant,restaurant)
                .where(findRestaurant(rid))
                .orderBy(review.createdDate.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory.select(review.count())
                .from(review)
                .leftJoin(review.member, member)
                .where(findRestaurant(rid));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    @Override
    public Page<ReviewSearchDto> findAllRestaurantReview(Long rid, ReviewSearch reviewSearch, Pageable pageable) {

        List<Review> reviewFetch = queryFactory
                .selectFrom(review)
                .join(review.member, member).fetchJoin()
                .join(review.restaurant,restaurant).fetchJoin()
                .where(findRestaurant(rid))
                .orderBy(review.createdDate.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<ReviewSearchDto> content = reviewFetch.stream()
                .map(review -> new ReviewSearchDto(review))
                .collect(Collectors.toList());

        content.forEach(dto -> log.info("dto : {}",dto));

        JPAQuery<Long> countQuery = queryFactory.select(review.count())
                .from(review)
                .leftJoin(review.member, member)
                .where(findRestaurant(rid));


        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private BooleanExpression findRestaurant(Long rid) {
        return (rid==null)?null:review.restaurant.id.eq(rid);
    }
}
