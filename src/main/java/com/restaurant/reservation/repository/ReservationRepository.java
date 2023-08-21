package com.restaurant.reservation.repository;

import com.restaurant.reservation.domain.booking.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Long> , ReservatinoRepositoryCustom{

    /** 단순 조회 */
    @Query("select r From Reservation r" +
            " where r.member.id=:id and r.date >= CURRENT_DATE" +
            " order by r.date,r.time")
    List<Reservation> findReservationAdvance(@Param("id") Long id);


    @Query("select r From Reservation r" +
            " where r.member.id=:id and r.date <= CURRENT_DATE and r.status = 'COMPLETE'" +
            " order by r.date,r.time")
    List<Reservation> findReservationComplete(@Param("id") Long id);

    @Query("select r From Reservation r" +
            " where r.member.id=:id and r.date <= CURRENT_DATE and r.status = 'NOSHOW'" +
            " order by r.date,r.time")
    List<Reservation> findReservationNoShow(@Param("id") Long id);

    Optional<Reservation> findReservationByIdAndMember_Id(Long rid, Long memberId);

    @Query("SELECT r FROM Reservation r " +
            "JOIN FETCH r.member m WHERE r.id = :rid AND m.id = :memberId")
    Optional<Reservation> fetchReservationByIdMember_Id(@Param("rid") Long rid, @Param("memberId") Long memberId);




    /** Service 로직 */

}
