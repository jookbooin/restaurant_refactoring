package com.restaurant.reservation;

import com.restaurant.reservation.domain.Menu;
import com.restaurant.reservation.domain.Tables;
import com.restaurant.reservation.domain.booking.Reservation;
import com.restaurant.reservation.domain.dto.MemberDto;
import com.restaurant.reservation.domain.dto.MenuDto;
import com.restaurant.reservation.domain.dto.OrderMenuDto;
import com.restaurant.reservation.domain.dto.ReservationDto;
import com.restaurant.reservation.domain.enumType.BookingStatus;
import com.restaurant.reservation.domain.enumType.MenuType;
import com.restaurant.reservation.domain.enumType.TableType;
import com.restaurant.reservation.domain.members.Member;
import com.restaurant.reservation.repository.MemberRepository;
import com.restaurant.reservation.repository.MenuRepository;
import com.restaurant.reservation.repository.TableRepository;
import com.restaurant.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct // bean에 올라오면 spring이 불러오는 것 : 초기화
    public void init() {
        initService.InitDb();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;
        private final MemberRepository memberRepository;
        private final TableRepository tableRepository;
        private final MenuRepository menuRepository;

        private final ReservationService reservationService;

        public void InitDb() {

            /** Member */
            MemberDto memberDto1 =MemberDto.builder()
                    .email("3670lsh@naver.com")
                    .password("dltmdgjs4139!")
                    .name("고객3670")
                    .phoneNumber("01071974139")
                    .build();
            Member member1= Member.createCustomer(memberDto1);
            Member insertMember1 = memberRepository.save(member1);

            MemberDto memberDto2=MemberDto.builder()
                    .email("3670lsh@gmail.com")
                    .password("dltmdgjs4139!")
                    .name("관리자3670")
                    .phoneNumber("01041397197")
                    .build();
            Member member2= Member.createAdmin(memberDto2);
            Member insertMember2 = memberRepository.save(member2);

            /** Table */
            Tables table1 =Tables.createTables(1,4, TableType.BAR);
            Tables table2 =Tables.createTables(2,4, TableType.HOLE);
            Tables table3 =Tables.createTables(3,8, TableType.ROOM);
            Tables table4 =Tables.createTables(4,4, TableType.TERRACE);

            Tables bar = tableRepository.save(table1);
            Tables hole = tableRepository.save(table2);
            Tables room = tableRepository.save(table3);
            Tables terrace = tableRepository.save(table4);

            /** menu */
            MenuDto menuDto1 = new MenuDto();
            menuDto1.setName("코스A");
            menuDto1.setPrice(80000);
            menuDto1.setIntro("스테이크2 + 샐러드1 + 사이드 1");
            menuDto1.setMenuType(MenuType.SPECIAL);
            Menu special1 = Menu.createMenu(menuDto1);

            MenuDto menuDto2 = new MenuDto();
            menuDto2.setName("코스B");
            menuDto2.setPrice(95000);
            menuDto2.setIntro("해산물2 + 사이드 2");
            menuDto2.setMenuType(MenuType.SPECIAL);
            Menu special2 = Menu.createMenu(menuDto2);

            MenuDto menuDto3 = new MenuDto();
            menuDto3.setName("코스C");
            menuDto3.setPrice(110000);
            menuDto3.setIntro("스테이크2 + 버거2 + 사이드 1 + 음료4");
            menuDto3.setMenuType(MenuType.SPECIAL);
            Menu special3 = Menu.createMenu(menuDto3);

            MenuDto menuDto4 = new MenuDto();
            menuDto4.setName("스테이크");
            menuDto4.setPrice(25000);
            menuDto4.setIntro("스테이크 입니다");
            menuDto4.setMenuType(MenuType.GENERAL);
            Menu general1 = Menu.createMenu(menuDto4);

            MenuDto menuDto5 = new MenuDto();
            menuDto5.setName("버거");
            menuDto5.setPrice(16000);
            menuDto5.setIntro("수제버거 입니다");
            menuDto5.setMenuType(MenuType.GENERAL);
            Menu general2 = Menu.createMenu(menuDto5);

            Menu specialA = menuRepository.save(special1);
            Menu specialB = menuRepository.save(special2);
            Menu specialC = menuRepository.save(special3);
            Menu generalA = menuRepository.save(general1);
            Menu generalB = menuRepository.save(general2);

            /** OrderMenu */
            OrderMenuDto orderMenuDto1 = OrderMenuDto.builder().menuId(specialA.getId()).count(1).build();
            OrderMenuDto orderMenuDto2 = OrderMenuDto.builder().menuId(specialA.getId()).count(6).build();

            OrderMenuDto orderMenuDto3 = OrderMenuDto.builder().menuId(specialB.getId()).count(2).build();

            OrderMenuDto orderMenuDto4 = OrderMenuDto.builder().menuId(specialC.getId()).count(3).build();
            OrderMenuDto orderMenuDto5 = OrderMenuDto.builder().menuId(specialC.getId()).count(4).build();
            OrderMenuDto orderMenuDto6 = OrderMenuDto.builder().menuId(specialC.getId()).count(4).build();

            OrderMenuDto orderMenuDto7 = OrderMenuDto.builder().menuId(generalA.getId()).count(3).build();
            OrderMenuDto orderMenuDto8 = OrderMenuDto.builder().menuId(generalA.getId()).count(5).build();

            OrderMenuDto orderMenuDto9 = OrderMenuDto.builder().menuId(generalB.getId()).count(3).build();

            /** */

            List<OrderMenuDto> orderMenuDtoList1= new ArrayList<>();
            orderMenuDtoList1.add(orderMenuDto1);
            orderMenuDtoList1.add(orderMenuDto2);
            orderMenuDtoList1.add(orderMenuDto3);

            List<OrderMenuDto> orderMenuDtoList2= new ArrayList<>();
            orderMenuDtoList2.add(orderMenuDto4);
            orderMenuDtoList2.add(orderMenuDto5);


            List<OrderMenuDto> orderMenuDtoList3= new ArrayList<>();
            orderMenuDtoList3.add(orderMenuDto6);
            orderMenuDtoList3.add(orderMenuDto7);

            List<OrderMenuDto> orderMenuDtoList4= new ArrayList<>();
            orderMenuDtoList4.add(orderMenuDto8);
            orderMenuDtoList4.add(orderMenuDto9);

            List<OrderMenuDto> orderMenuDtoList5= new ArrayList<>();
            orderMenuDtoList5.add(orderMenuDto1);
            orderMenuDtoList5.add(orderMenuDto8);
            orderMenuDtoList5.add(orderMenuDto9);

            /** Date Time */
            LocalDate date1 = LocalDate.of(2023, 7, 12);
            LocalTime time1 = LocalTime.of(16, 00, 0);

            LocalDate date2 = LocalDate.of(2023, 7, 17);
            LocalTime time2 = LocalTime.of(12, 0, 0);

            LocalDate date3 = LocalDate.of(2023, 7, 20);
            LocalTime time3 = LocalTime.of(20, 30, 0);

            LocalDate date4 = LocalDate.of(2023, 7, 20);
            LocalTime time4 = LocalTime.of(20, 30, 0);

            LocalDate date5 = LocalDate.of(2023, 7, 21);
            LocalTime time5 = LocalTime.of(14, 0, 0);

            LocalDate date6 = LocalDate.of(2023, 10, 21);
            LocalTime time6 = LocalTime.of(17, 0, 0);

            LocalDate date7 = LocalDate.of(2023, 10, 21);
            LocalTime time7 = LocalTime.of(18, 0, 0);

            LocalDate date8 = LocalDate.of(2023, 11, 21);
            LocalTime time8 = LocalTime.of(19, 0, 0);


            /** Reservation */

            ReservationDto reservationDto1 = ReservationDto.builder()
                    .date(date1).time(time1).number(4).orderMenuList(orderMenuDtoList1).build();

            ReservationDto reservationDto2 = ReservationDto.builder()
                    .date(date2).time(time2).number(3).orderMenuList(orderMenuDtoList2).build();

            ReservationDto reservationDto3 = ReservationDto.builder()
                    .date(date3).time(time3).number(8).orderMenuList(orderMenuDtoList3).build();

            ReservationDto reservationDto4 = ReservationDto.builder()
                    .date(date4).time(time4).number(6).orderMenuList(orderMenuDtoList4).build();

            ReservationDto reservationDto5 = ReservationDto.builder()
                    .date(date5).time(time5).number(6).orderMenuList(orderMenuDtoList1).build();

            ReservationDto reservationDto6 = ReservationDto.builder()
                    .date(date6).time(time6).number(3).orderMenuList(orderMenuDtoList2).build();

            ReservationDto reservationDto7 = ReservationDto.builder()
                    .date(date7).time(time7).number(7).orderMenuList(orderMenuDtoList3).build();

            ReservationDto reservationDto8 = ReservationDto.builder()
                    .date(date8).time(time8).number(4).orderMenuList(orderMenuDtoList4).build();

            /** 지난 날짜 */
            Reservation reservation1 = reservationService.addReservation(member1.getId(), reservationDto1);
            reservation1.chageBookingStatus(BookingStatus.COMPLETE);

            Reservation reservation2 = reservationService.addReservation(member1.getId(), reservationDto2);
            reservation2.chageBookingStatus(BookingStatus.NOSHOW);
//
            Reservation reservation3 = reservationService.addReservation(member1.getId(), reservationDto3);
            reservation3.chageBookingStatus(BookingStatus.COMPLETE);
//
            Reservation reservation4 = reservationService.addReservation(member1.getId(), reservationDto4);
            reservation4.chageBookingStatus(BookingStatus.NOSHOW);
//
            Reservation reservation5 = reservationService.addReservation(member1.getId(), reservationDto5);
            reservation5.chageBookingStatus(BookingStatus.COMPLETE);
//
//
//            /** pre */
            Reservation reservation6 = reservationService.addReservation(member1.getId(), reservationDto6);
            Reservation reservation7 = reservationService.addReservation(member1.getId(), reservationDto7);
            Reservation reservation8 = reservationService.addReservation(member1.getId(), reservationDto8);

            /** Reservation */

        }

    }
}
