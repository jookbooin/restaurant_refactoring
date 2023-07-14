package com.restaurant.reservation.repository;

import com.restaurant.reservation.domain.Tables;
import com.restaurant.reservation.domain.booking.Reservation;
import com.restaurant.reservation.domain.booking.Waiting;
import com.restaurant.reservation.domain.enumType.MemberType;
import com.restaurant.reservation.domain.members.Member;
import com.restaurant.reservation.repository.entityManagerRepo.ReservationRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

@SpringBootTest
@Transactional

class ReservationRepositoryTest {

    @Autowired
    ReservationRepo reservationRepo;
    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    WaitingRepository waitingRepository;

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    TableRepository tableRepository;
    @Test
    @Rollback(false)
    public void reservationInsert() throws Exception{

        Member findmember = memberRepository.findByEmail("3670lsh@naver.com").get();
        // given
        Reservation reservation = new Reservation();
        LocalDate todayDate = LocalDate.of(2023, 7, 12);
        LocalTime todayTime = LocalTime.of(23, 30, 0);
        Tables findTables1 = tableRepository.findById(1L).get();
        System.out.println("todayTime = " + todayTime);
        reservation.setDate(todayDate);
        reservation.setTime(todayTime);
        reservation.setNumber(5);
        reservation.setMember(findmember);
        reservation.setTables(findTables1);

        Reservation saveReservation = reservationRepository.save(reservation);
        Assertions.assertEquals(saveReservation.getId(),1L);
        System.out.println("saveReservation.getTime() = " + saveReservation.getTime());

        Reservation reservation1 = reservationRepository.findById(1L).orElse(null); // 영속성에 있나?
        System.out.println("reservation1 = " + reservation1.getDate()+ " , " + reservation1.getTime());

        Waiting waiting = new Waiting();
        Tables findTables2 = tableRepository.findById(2L).get();
        waiting.setDate(todayDate);
        waiting.setNumber(4);
        waiting.setMemberType(MemberType.GUEST);
        Waiting saveWaiting = waitingRepository.save(waiting);
        Assertions.assertEquals(saveWaiting.getId(),2L);
        Waiting waiting1 = waitingRepository.findById(2L).orElse(null); // 영속성에 있나?
        System.out.println("waiting1 = " + waiting1.getDate());
    }
}