package com.restaurant.reservation.repository;

import com.restaurant.reservation.domain.booking.Reservation;
import com.restaurant.reservation.domain.dto.ReservationDto;
import com.restaurant.reservation.domain.enumType.BookingStatus;
import com.restaurant.reservation.domain.enumType.MemberGrade;
import com.restaurant.reservation.domain.enumType.MemberRole;
import com.restaurant.reservation.domain.members.Member;
import com.restaurant.reservation.domain.members.MemberInfo;
import com.restaurant.reservation.repository.dto.BookingSearch;
import com.restaurant.reservation.repository.dto.BookingSearchDto;
import com.restaurant.reservation.service.ReservationService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@Transactional
class ReservationRepositoryImplTest {
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ReservationService reservationService;
    @Autowired
    ReservationRepository reservationRepository;

    @BeforeEach
    public void before(){
        /** Member */
        List<String> emailList = Arrays.asList("@naver.com","@gmail.com","@empal.com","@kakao.com");
        List<String> nameList = Arrays.asList("이승헌","감스트","죽부인","마우스");
        List<String> phoneList = Arrays.asList("02","010","031","052");

        for (int i = 3; i < 103; i++) {
            int four = i % 4 ;


            MemberInfo info = new MemberInfo(nameList.get(four),"010"+i);
            Member member = null;

            if (i%4==1){
                member = new Member(emailList.get(1), "1", info , MemberGrade.BRONZE, MemberRole.CUSTOMER);
            }
            else if (i%4==2){
                member = new Member(emailList.get(2), "1", info , MemberGrade.SILVER, MemberRole.CUSTOMER);
            }
            else if (i%4==3)
                member =  new Member(emailList.get(0), "1", info , MemberGrade.GOLD, MemberRole.CUSTOMER);
            else
              member =  new Member(emailList.get(0), "1", info , MemberGrade.BRONZE, MemberRole.CUSTOMER);

            memberRepository.save(member);
        }


        /** Date Time */
        LocalDate date1 = LocalDate.of(2023, 7, 12);
        LocalTime time1 = LocalTime.of(16, 00, 0);

        LocalDate date2 = LocalDate.of(2023, 7, 17);
        LocalTime time2 = LocalTime.of(12, 0, 0);

        LocalDate date3 = LocalDate.of(2023, 7, 20);
        LocalTime time3 = LocalTime.of(20, 30, 0);
        LocalDate date4 = LocalDate.of(2023, 7, 20);
        LocalTime time4 = LocalTime.of(20, 30, 0);
        LocalDate date5 = LocalDate.of(2023, 7, 30);
        LocalTime time5 = LocalTime.of(14, 0, 0);
        /** 9월 */
        LocalDate date6 = LocalDate.of(2023, 9, 10);
        LocalTime time6 = LocalTime.of(17, 0, 0);
        LocalDate date7 = LocalDate.of(2023, 9, 10);
        LocalTime time7 = LocalTime.of(18, 0, 0);
        LocalDate date8 = LocalDate.of(2023, 9, 10);
        LocalTime time8 = LocalTime.of(19, 0, 0);
        LocalDate date9 = LocalDate.of(2023, 9, 21);
        LocalTime time9 = LocalTime.of(19, 0, 0);
        /** 10월 */
        LocalDate date10 = LocalDate.of(2023, 10, 11);
        LocalTime time10 = LocalTime.of(19, 0, 0);
        LocalDate date11 = LocalDate.of(2023, 10, 11);
        LocalTime time11 = LocalTime.of(19, 0, 0);
        LocalDate date12 = LocalDate.of(2023, 10, 12);
        LocalTime time12 = LocalTime.of(19, 0, 0);
        LocalDate date13 = LocalDate.of(2023, 10, 13);
        LocalTime time13 = LocalTime.of(19, 0, 0);
        LocalDate date14 = LocalDate.of(2023, 10, 14);
        LocalTime time14 = LocalTime.of(15, 0, 0);
        LocalDate date15 = LocalDate.of(2023, 10, 15);
        LocalTime time15 = LocalTime.of(12, 0, 0);
        LocalDate date16 = LocalDate.of(2023, 10, 16);
        LocalTime time16 = LocalTime.of(16, 0, 0);
        LocalDate date17 = LocalDate.of(2023, 10, 17);
        LocalTime time17 = LocalTime.of(14, 0, 0);
        LocalDate date18 = LocalDate.of(2023, 10, 18);
        LocalTime time18 = LocalTime.of(19, 0, 0);
        LocalDate date19 = LocalDate.of(2023, 10, 19);
        LocalTime time19 = LocalTime.of(12, 0, 0);
        LocalDate date20 = LocalDate.of(2023, 10, 20);
        LocalTime time20 = LocalTime.of(15, 0, 0);


        /** Reservation*/
        ReservationDto reservationDto1 = ReservationDto.builder().date(date1).time(time1).number(4).build();
        ReservationDto reservationDto2 = ReservationDto.builder().date(date2).time(time2).number(3).build();
        ReservationDto reservationDto3 = ReservationDto.builder().date(date3).time(time3).number(8).build();
        ReservationDto reservationDto4 = ReservationDto.builder().date(date4).time(time4).number(6).build();
        ReservationDto reservationDto5 = ReservationDto.builder().date(date5).time(time5).number(6).build();

        ReservationDto reservationDto6 = ReservationDto.builder().date(date6).time(time6).number(3).build();
        ReservationDto reservationDto7 = ReservationDto.builder().date(date7).time(time7).number(7).build();
        ReservationDto reservationDto8 = ReservationDto.builder().date(date8).time(time8).number(4).build();
        ReservationDto reservationDto9 = ReservationDto.builder() .date(date9).time(time9).number(4).build();

        ReservationDto reservationDto10 = ReservationDto.builder().date(date10).time(time10).number(4).build();
        ReservationDto reservationDto11 = ReservationDto.builder().date(date11).time(time11).number(2).build();
        ReservationDto reservationDto12 = ReservationDto.builder().date(date12).time(time12).number(3).build();
        ReservationDto reservationDto13 = ReservationDto.builder().date(date13).time(time13).number(6).build();
        ReservationDto reservationDto14 = ReservationDto.builder().date(date14).time(time14).number(4).build();
        ReservationDto reservationDto15 = ReservationDto.builder().date(date15).time(time15).number(8).build();
        ReservationDto reservationDto16 = ReservationDto.builder().date(date16).time(time16).number(4).build();
        ReservationDto reservationDto17 = ReservationDto.builder().date(date17).time(time17).number(5).build();
        ReservationDto reservationDto18 = ReservationDto.builder().date(date18).time(time18).number(6).build();
        ReservationDto reservationDto19 = ReservationDto.builder().date(date19).time(time19).number(3).build();
        ReservationDto reservationDto20 = ReservationDto.builder().date(date20).time(time20).number(7).build();

        Reservation reservation1 = reservationService.addReservation(1L, reservationDto1);
        reservation1.chageBookingStatus(BookingStatus.COMPLETE);

        Reservation reservation2 = reservationService.addReservation(2L, reservationDto2);
        reservation2.chageBookingStatus(BookingStatus.NOSHOW);
//
        Reservation reservation3 = reservationService.addReservation(3L, reservationDto3);
        reservation3.chageBookingStatus(BookingStatus.COMPLETE);
//
        Reservation reservation4 = reservationService.addReservation(4L, reservationDto4);
        reservation4.chageBookingStatus(BookingStatus.NOSHOW);
//
        Reservation reservation5 = reservationService.addReservation(5L, reservationDto5);
        reservation5.chageBookingStatus(BookingStatus.COMPLETE);

        /** pre */
        Reservation reservation6 = reservationService.addReservation(6L, reservationDto6);
        Reservation reservation7 = reservationService.addReservation(7L, reservationDto7);
        Reservation reservation8 = reservationService.addReservation(8L, reservationDto8);
        Reservation reservation9 = reservationService.addReservation(9L, reservationDto9);

        Reservation reservation10 = reservationService.addReservation(10L, reservationDto10);
        Reservation reservation11 = reservationService.addReservation(11L, reservationDto11);
        Reservation reservation12 = reservationService.addReservation(12L, reservationDto12);
        Reservation reservation13 = reservationService.addReservation(13L, reservationDto13);
        Reservation reservation14 = reservationService.addReservation(14L, reservationDto14);
        Reservation reservation15 = reservationService.addReservation(15L, reservationDto15);
        Reservation reservation16 = reservationService.addReservation(16L, reservationDto16);
        Reservation reservation17 = reservationService.addReservation(17L, reservationDto17);
        Reservation reservation18 = reservationService.addReservation(18L, reservationDto18);
        Reservation reservation19 = reservationService.addReservation(19L, reservationDto19);
        Reservation reservation20 = reservationService.addReservation(20L, reservationDto20);

    }
    @Test
    void findAllAdvanceReservation() {
        BookingSearch searchCondition = new BookingSearch();
        LocalDate startDate = LocalDate.of(2023,9,11);
//        LocalDate endDate = LocalDate.of(2023,9,30);
        searchCondition.setStartDate(startDate);
//        searchCondition.setEndDate(endDate);

        PageRequest pageRequest = PageRequest.of(0,10);

        Page<BookingSearchDto> result = reservationRepository.findAllAdvanceReservation(searchCondition, pageRequest);

        Assertions.assertThat(result.getSize()).isEqualTo(10);
        Assertions.assertThat(result.getTotalElements()).isEqualTo(12);
        Assertions.assertThat(result.getTotalPages()).isEqualTo(2);

        List<BookingSearchDto> content = result.getContent();
//        Assertions.assertThat(content.size()).isEqualTo(5);
        for (BookingSearchDto searchDto : content) {
            System.out.println("searchDto = " + searchDto);
        }

    }
}