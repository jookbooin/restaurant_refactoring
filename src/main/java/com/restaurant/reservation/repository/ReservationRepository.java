package com.restaurant.reservation.repository;

import com.restaurant.reservation.domain.booking.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Long> {

    /** 단순 조회 */
    @Query("select r From Reservation r" +
            " where r.member.id=:id and r.date >= CURRENT_DATE" +
            " order by r.date,r.time")
    List<Reservation> findReservationPre(@Param("id") Long id);

    @Query("select r From Reservation r" +
            " where r.member.id=:id and r.date <= CURRENT_DATE and r.status = 'COMPLETE'" +
            " order by r.date,r.time")
    List<Reservation> findReservationComplete(@Param("id") Long id);

    @Query("select r From Reservation r" +
            " where r.member.id=:id and r.date <= CURRENT_DATE and r.status = 'NOSHOW'" +
            " order by r.date,r.time")
    List<Reservation> findReservationNoShow(@Param("id") Long id);



    /** Service 로직 */

}
