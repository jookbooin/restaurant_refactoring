package com.restaurant.reservation.repository;

import com.restaurant.reservation.domain.Tables;
import com.restaurant.reservation.domain.booking.Reservation;
import com.restaurant.reservation.domain.booking.Waiting;
import com.restaurant.reservation.domain.dto.MemberDto;
import com.restaurant.reservation.domain.dto.ReservationDto;
import com.restaurant.reservation.domain.enumType.MemberRole;
import com.restaurant.reservation.domain.enumType.TimeEnum;
import com.restaurant.reservation.domain.members.Member;
import com.restaurant.reservation.repository.entityManagerRepo.ReservationRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

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

    @Autowired
    EntityManager em;

    @Profile("test")
    @BeforeEach
    public void before(){
        MemberDto memberDto = MemberDto.builder()
                .email("3670lsh@naver.con")
                .password("dltmdgjs4139")
                .name("고객")
                .phoneNumber("01071974139")
                .build();
        Member insertMember = Member.createCustomer(memberDto);

        Member member = memberRepository.save(insertMember);

        LocalDate date = LocalDate.of(2023, 10, 11);
        LocalTime time1 = LocalTime.of(10, 0, 0);
        LocalTime time2 = LocalTime.of(12, 0, 0);
        LocalTime time3 = LocalTime.of(14, 0, 0);
        LocalTime time4 = LocalTime.of(16, 0, 0);
        LocalTime time5 = LocalTime.of(18, 0, 0);

        ReservationDto reservationDto1 = ReservationDto.builder().date(date).time(time1).number(3).build();
        ReservationDto reservationDto2 = ReservationDto.builder().date(date).time(time2).number(4).build();
        ReservationDto reservationDto3 = ReservationDto.builder().date(date).time(time3).number(5).build();
        ReservationDto reservationDto4 = ReservationDto.builder().date(date).time(time4).number(6).build();
        ReservationDto reservationDto5 = ReservationDto.builder().date(date).time(time5).number(7).build();

        for(int i = 0 ; i< 3 ; i ++){
            Reservation reservation = Reservation.createReservation(member, reservationDto1, null);
            reservationRepository.save(reservation);
        }
        for(int i = 0 ; i< 4 ; i ++){
            Reservation reservation = Reservation.createReservation(member, reservationDto2, null);
            reservationRepository.save(reservation);
        }
        for(int i = 0 ; i< 5 ; i ++){
            Reservation reservation = Reservation.createReservation(member, reservationDto3, null);
            reservationRepository.save(reservation);
        }
        for(int i = 0 ; i< 5 ; i ++){
            Reservation reservation = Reservation.createReservation(member, reservationDto4, null);
            reservationRepository.save(reservation);
        }
        for(int i = 0 ; i< 2 ; i ++){
            Reservation reservation = Reservation.createReservation(member, reservationDto5, null);
            reservationRepository.save(reservation);
        }
    }

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
//        reservation.setTables(findTables1);

        Reservation saveReservation = reservationRepository.save(reservation);
        Assertions.assertEquals(saveReservation.getId(),1L);
        System.out.println("saveReservation.getTime() = " + saveReservation.getTime());

        Reservation reservation1 = reservationRepository.findById(1L).orElse(null); // 영속성에 있나?
        System.out.println("reservation1 = " + reservation1.getDate()+ " , " + reservation1.getTime());

        Waiting waiting = new Waiting();
        Tables findTables2 = tableRepository.findById(2L).get();
//        waiting.setDate(todayDate);
        waiting.setNumber(4);
        waiting.setMemberRole(MemberRole.GUEST);
        Waiting saveWaiting = waitingRepository.save(waiting);
        Assertions.assertEquals(saveWaiting.getId(),2L);
        Waiting waiting1 = waitingRepository.findById(2L).orElse(null); // 영속성에 있나?
//        System.out.println("waiting1 = " + waiting1.getDate());
    }

    @Test
    public void 주문_조회() throws Exception {

//        List<Reservation> allReservation = reservationRepository.findAll();
//
//        allReservation.forEach(o->{
//            System.out.println("o.getId() = " + o.getId());
//            System.out.println("o.getNumber() = " + o.getNumber());
//            System.out.println("o.getDate() = " + o.getDate());
//            System.out.println("o.getTime() = " + o.getTime());
//            System.out.println("o.getMember() = " + o.getMember());
//            System.out.println();
//        });

        Member member = memberRepository.findById(1L).get();

        System.out.println("== @query ==");
        List<Reservation> queryList = reservationRepository.findReservationAdvance(member.getId());
        queryList.forEach(o->{
            System.out.println(o.getId());
            System.out.println(o.getDate());
            System.out.println(o.getTime());
            System.out.println(o.getMember().getMemberInfo().getName());

            System.out.println();
        });

//        List<Reservation> reservationList = reservationRepository.findByMemberIdOrderByDateAscTimeAsc(member.getId());
//        reservationList.forEach(o -> {
//            System.out.println(o.getId());
//            System.out.println(o.getDate());
//            System.out.println(o.getTime());
//        });
    }

    @Test
    public void queryMethodTest() throws Exception{

//        Member member = memberRepository.findById(1L).get();
        Long sessionId =1L;
        Long reservaionId = 6L;
        Optional<Reservation> findReservationOpt = reservationRepository.findReservationByIdAndMember_Id(reservaionId, sessionId);
        findReservationOpt.ifPresent(findReservation-> System.out.println(findReservation.toString()));

    }

    @Test
    public void TimeCount() throws Exception{

//        BookingSearch bookingSearch = new BookingSearch();
//        PageRequest pageRequest = PageRequest.of(0,20);

        LocalDate date = LocalDate.of(2023,10,11);
        List<Object[]> objectList = reservationRepository.findPossibleTimeByDate(date, 5L);
        assertThat(objectList.size()).isEqualTo(2);
        List<LocalTime> possibleTime = objectList.stream().map(objects -> (LocalTime) objects[0])
                .collect(Collectors.toList());
        for (LocalTime localTime : possibleTime) {
            System.out.println("localTime = " + localTime);
        }

        List<TimeEnum> timeList = Arrays.asList(TimeEnum.values());


    }


}