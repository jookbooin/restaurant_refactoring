package com.restaurant.reservation.repository.custom;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.restaurant.reservation.domain.booking.Reservation;
import com.restaurant.reservation.domain.enumType.BookingStatus;
import com.restaurant.reservation.repository.custom.ReservatinoRepositoryCustom;
import com.restaurant.reservation.repository.dto.BookingSearch;
import com.restaurant.reservation.repository.dto.BookingSearchDto;
import com.restaurant.reservation.repository.dto.QBookingSearchDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static com.restaurant.reservation.domain.booking.QReservation.reservation;
import static com.restaurant.reservation.domain.members.QMember.member;

@Slf4j
public class ReservationRepositoryImpl implements ReservatinoRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public ReservationRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    //방문 예정 예약
    @Override
    public Page<BookingSearchDto> findAllAdvanceReservation(BookingSearch bookingSearch, Pageable pageable) {
        List<BookingSearchDto> content = queryFactory
                .select(new QBookingSearchDto(reservation.id,
                        reservation.date,
                        reservation.time,
                        member.id.as("memberId"),
                        member.memberInfo.name,
                        member.memberInfo.phoneNumber,
                        reservation.number,
                        reservation.modifiedDate))
                .from(reservation)
                .join(reservation.member, member)
                .where(
                        betweenDate(bookingSearch.getStartDate(), bookingSearch.getEndDate()),
                        searchCondition(bookingSearch.getSearchType(),bookingSearch.getKeyword()),
                        statusEq(BookingStatus.ADVANCE),
                        currentDateTimeGoe(LocalDateTime.now())  // 현재 날짜 시간 이후
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(reservation.date.asc(), reservation.time.asc(),reservation.number.asc())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(reservation.count())
                .from(reservation)
                .join(reservation.member, member)
                .where(
                        betweenDate(bookingSearch.getStartDate(), bookingSearch.getEndDate()),
                        searchCondition(bookingSearch.getSearchType(),bookingSearch.getKeyword()),
                        statusEq(BookingStatus.ADVANCE),
                        currentDateTimeGoe(LocalDateTime.now()) // 현재 날짜 시간 이후

                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private BooleanExpression currentDateTimeGoe(LocalDateTime now) {
        LocalDate currentDate = now.toLocalDate();
        LocalTime currentTime = now.toLocalTime();
        log.info("currentDate : {}",currentDate);
        log.info("currentTime : {}",currentTime);
        return dateGoe(currentDate);
    }


    private BooleanExpression searchCondition(String searchType, String keyword) {
        if(StringUtils.hasText(searchType)){
            log.info("0. YES searchType");
            String trimKeyword = keyword.trim();
            if(searchType.equals("name")){
                log.info("1. searchType -name");
                return StringUtils.hasText(trimKeyword) ? member.memberInfo.name.contains(trimKeyword) : null;
            }
            else if (searchType.equals("phoneNumber")){
                log.info("2. searchType -phoneNumber");
                return StringUtils.hasText(trimKeyword) ? member.memberInfo.phoneNumber.contains(trimKeyword) : null ;

            }else if (searchType.equals("number")){
                log.info("3. searchType -number");
                return StringUtils.hasText(trimKeyword) ? reservation.number.eq(Integer.parseInt(trimKeyword)) : null;
            }

        }
        log.info("0. NO searchType");
        return null;
    }

    private BooleanExpression phoneNumberContain(String phoneNumber) {
        return StringUtils.hasText(phoneNumber) ? reservation.member.memberInfo.phoneNumber.contains(phoneNumber):null;
    }

    private BooleanExpression statusEq(BookingStatus advance) {
        return advance != null ? reservation.status.eq(advance) : null;
    }

    private BooleanExpression numberEq(Integer number) {
        return number != null?reservation.number.eq(number):null;
    }

    private BooleanExpression nameContain(String name) {
       return StringUtils.hasText(name) ? reservation.member.memberInfo.name.contains(name):null;
    }

    private BooleanExpression betweenDate(LocalDate startDate, LocalDate endDate) {
        log.info("startDate : {} endDate : {} ",startDate,endDate);
        if(startDate != null & endDate != null){
            log.info("1. startDate != null & endDate != null");
           return dateGoe(startDate).and(dateLoe(endDate));
        }else if (startDate != null){
            log.info("2. startDate != null");
            return dateGoe(startDate);
        }else if( endDate != null) {
            log.info("3. endDate != null");
            return dateLoe(endDate);
        }
            log.info("0. NO -startDate -endDate");
        return null;
    }

    private BooleanExpression dateLoe(LocalDate dateLoe) {
        return reservation.date.loe(dateLoe);
    }

    private BooleanExpression timeGoe(LocalTime timeGoe) {
        return reservation.time.goe(timeGoe);
    }

    private BooleanExpression timeLoe(LocalTime timeLoe) {
        return reservation.time.loe(timeLoe);
    }

    private BooleanExpression dateGoe(LocalDate dateGoe) {
        return reservation.date.goe(dateGoe);
    }

    // 전체 예약
    @Override
    public Page<Reservation> findAllReservation(BookingSearch bookingSearch, Pageable pageable) {
        return null;
    }


}
