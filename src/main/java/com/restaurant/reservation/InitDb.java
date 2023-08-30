package com.restaurant.reservation;

import com.restaurant.reservation.domain.Category;
import com.restaurant.reservation.domain.CategoryMenu;
import com.restaurant.reservation.domain.Menu;
import com.restaurant.reservation.domain.Tables;
import com.restaurant.reservation.domain.booking.Reservation;
import com.restaurant.reservation.domain.enumType.*;
import com.restaurant.reservation.domain.members.Member;
import com.restaurant.reservation.domain.members.MemberInfo;
import com.restaurant.reservation.repository.*;
import com.restaurant.reservation.repository.dto.*;
import com.restaurant.reservation.service.CategoryService;
import com.restaurant.reservation.service.MenuService;
import com.restaurant.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Profile("local")
@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct // bean에 올라오면 spring이 불러오는 것 : 초기화
    public void init() {
//        initService.InitDb();
//        initService.plus_Member_Reservation();
        initService.Category_Menu();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;
        private final MemberRepository memberRepository;
        private final TableRepository tableRepository;
        private final MenuRepository menuRepository;
        private final MenuService menuService;

        private final ReservationService reservationService;
        private final CategoryService categoryService;
        private final CategoryRepository categoryRepository;
        private final CategoryMenuRepository categoryMenuRepository;

        @Transactional
        public void Category_Menu(){
            /** root 카테고리 생성 */
            CategoryDto rootDto1 = CategoryDto.builder().name("음식").code("A1").build();
            CategoryDto rootDto2 = CategoryDto.builder().name("책").code("A2").build();
            CategoryDto rootDto3 = CategoryDto.builder().name("영화").code("A3").build();

            Category A1 = categoryService.saveCategory(rootDto1);
            Category A2 = categoryService.saveCategory(rootDto2);
            Category A3 = categoryService.saveCategory(rootDto3);

            /** 하위 카테고리 생성 */
            CategoryDto rootDto1l1_1 = CategoryDto.builder().parent("음식").name("메인").code("B1").build();      // 메인
            CategoryDto rootDto1l1_2 = CategoryDto.builder().parent("음식").name("사이드").code("B2").build();    // 사이드
            CategoryDto rootDto1l1_3 = CategoryDto.builder().parent("음식").name("주류&음료").code("B3").build(); // 주류&음료
            CategoryDto rootDto1l2_1 = CategoryDto.builder().parent("주류&음료").name("주류").code("C1").build(); // 주류
            CategoryDto rootDto1l2_2 = CategoryDto.builder().parent("주류&음료").name("음료").code("C2").build(); // 음료
            CategoryDto rootDto1l2_3 = CategoryDto.builder().parent("메인").name("스페셜").code("C3").build();    // 스페셜
            CategoryDto rootDto1l2_4 = CategoryDto.builder().parent("메인").name("스테이크").code("C4").build();  // 스테이크
            CategoryDto rootDto1l2_5 = CategoryDto.builder().parent("메인").name("파스타").code("C5").build();    // 파스타

            Category A1B1 = categoryService.saveCategory(rootDto1l1_1);   // 메인
            Category A1B2 = categoryService.saveCategory(rootDto1l1_2);   // 사이드
            Category A1B3 = categoryService.saveCategory(rootDto1l1_3);   // 주류&음료
            Category A1B3C1 = categoryService.saveCategory(rootDto1l2_1);   // 주류
            Category A1B3C2 = categoryService.saveCategory(rootDto1l2_2);   // 음료
            Category A1B1C3 = categoryService.saveCategory(rootDto1l2_3);   // 스페셜
            Category A1B1C4 = categoryService.saveCategory(rootDto1l2_4);   // 스테이크
            Category A1B1C5 = categoryService.saveCategory(rootDto1l2_5);   // 파스타

            /** 메뉴 생성 */
            MenuDto menuDto10 = MenuDto.builder().name("처음처럼").price(5000).description("주류 - A1B3C1").build();
            MenuDto menuDto11 = MenuDto.builder().name("새로").price(4000).description("주류 - A1B3C1").build();
            MenuDto menuDto12 = MenuDto.builder().name("콜라").price(3000).description("음료 - A1B3C2").build();
            MenuDto menuDto20 = MenuDto.builder().name("토마호크").price(30000).description("스테이크 - A1B1C4").build();
            MenuDto menuDto30 = MenuDto.builder().name("알프레도").price(30000).description("파스타 - A1B1C5").build();
            MenuDto menuDto31 = MenuDto.builder().name("로제").price(31000).description("파스타 - A1B1C5").build();
            MenuDto menuDto32 = MenuDto.builder().name("라자냐").price(32000).description("파스타 - A1B1C5").build();
            MenuDto menuDto33 = MenuDto.builder().name("까르보나라").price(33000).description("파스타 - A1B1C5").build();
            MenuDto menuDto34 = MenuDto.builder().name("뽀모도로").price(34000).description("파스타 - A1B1C5").build();
            MenuDto menuDto35 = MenuDto.builder().name("볼로네즈").price(35000).description("파스타 - A1B1C5").build();
            MenuDto menuDto36 = MenuDto.builder().name("나폴리탄").price(36000).description("파스타 - A1B1C5").build();
            MenuDto menuDto37 = MenuDto.builder().name("푸타네스카").price(37000).description("파스타 - A1B1C5").build();
            MenuDto menuDto38 = MenuDto.builder().name("봉골레").price(38000).description("파스타 - A1B1C5").build();
            MenuDto menuDto39 = MenuDto.builder().name("알리오올리오").price(39000).description("파스타 - A1B1C5").build();
            MenuDto menuDto50 = MenuDto.builder().name("감자튀김").price(6000).description("사이드 - A1B2").build();
            MenuDto menuDto51 = MenuDto.builder().name("콘치즈").price(4000).description("사이드 - A1B2").build();
            MenuDto menuDto52 = MenuDto.builder().name("볶음밥").price(7000).description("사이드 - A1B2").build();

            Menu menu10 = menuRepository.save(Menu.createMenu(menuDto10)); // 처음처럼
            Menu menu11 = menuRepository.save(Menu.createMenu(menuDto11)); // 새로
            Menu menu12 = menuRepository.save(Menu.createMenu(menuDto12)); // 콜라
            Menu menu20 = menuRepository.save(Menu.createMenu(menuDto20)); //
            Menu menu30 = menuRepository.save(Menu.createMenu(menuDto30)); //
            Menu menu31 = menuRepository.save(Menu.createMenu(menuDto31)); //
            Menu menu32 = menuRepository.save(Menu.createMenu(menuDto32)); //
            Menu menu33 = menuRepository.save(Menu.createMenu(menuDto33)); //
            Menu menu34 = menuRepository.save(Menu.createMenu(menuDto34)); //
            Menu menu35 = menuRepository.save(Menu.createMenu(menuDto35)); //
            Menu menu36 = menuRepository.save(Menu.createMenu(menuDto36)); //
            Menu menu37 = menuRepository.save(Menu.createMenu(menuDto37)); //
            Menu menu38 = menuRepository.save(Menu.createMenu(menuDto38)); //
            Menu menu39 = menuRepository.save(Menu.createMenu(menuDto39)); //
            Menu menu50 = menuRepository.save(Menu.createMenu(menuDto50)); //
            Menu menu51 = menuRepository.save(Menu.createMenu(menuDto51)); //
            Menu menu52 = menuRepository.save(Menu.createMenu(menuDto52)); //


            /** 카테고리 메뉴 */
            CategoryMenu cm10 = CategoryMenu.createCategoryMenu(menu10, A1B3C1);  // 주류
            CategoryMenu cm11 = CategoryMenu.createCategoryMenu(menu11, A1B3C1);
            CategoryMenu cm12 = CategoryMenu.createCategoryMenu(menu12, A1B3C2);  // 음료
            CategoryMenu cm20 = CategoryMenu.createCategoryMenu(menu20, A1B1C4);  // 스테이크
            CategoryMenu cm30 = CategoryMenu.createCategoryMenu(menu30, A1B1C5);  // 파스타
            CategoryMenu cm31 = CategoryMenu.createCategoryMenu(menu31, A1B1C5);
            CategoryMenu cm32 = CategoryMenu.createCategoryMenu(menu32, A1B1C5);
            CategoryMenu cm33 = CategoryMenu.createCategoryMenu(menu33, A1B1C5);
            CategoryMenu cm34 = CategoryMenu.createCategoryMenu(menu34, A1B1C5);
            CategoryMenu cm35 = CategoryMenu.createCategoryMenu(menu35, A1B1C5);
            CategoryMenu cm36 = CategoryMenu.createCategoryMenu(menu36, A1B1C5);
            CategoryMenu cm37 = CategoryMenu.createCategoryMenu(menu37, A1B1C5);
            CategoryMenu cm38 = CategoryMenu.createCategoryMenu(menu38, A1B1C5);
            CategoryMenu cm39 = CategoryMenu.createCategoryMenu(menu39, A1B1C5);
            CategoryMenu cm50 = CategoryMenu.createCategoryMenu(menu50, A1B1C5);
            CategoryMenu cm51 = CategoryMenu.createCategoryMenu(menu51, A1B1C5);
            CategoryMenu cm52 = CategoryMenu.createCategoryMenu(menu52, A1B1C5);
//
            categoryMenuRepository.save(cm10);
            categoryMenuRepository.save(cm11);
            categoryMenuRepository.save(cm12);
            categoryMenuRepository.save(cm20);
            categoryMenuRepository.save(cm30);
            categoryMenuRepository.save(cm31);
            categoryMenuRepository.save(cm32);
            categoryMenuRepository.save(cm33);
            categoryMenuRepository.save(cm34);
            categoryMenuRepository.save(cm35);
            categoryMenuRepository.save(cm36);
            categoryMenuRepository.save(cm37);
            categoryMenuRepository.save(cm38);
            categoryMenuRepository.save(cm39);
            categoryMenuRepository.save(cm50);
            categoryMenuRepository.save(cm51);
            categoryMenuRepository.save(cm52);

        }

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
                    .name("관리자")
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
            menuDto1.setDescription("스테이크2 + 샐러드1 + 사이드 1");
            menuDto1.setMenuType(MenuType.SPECIAL);
            Menu special1 = Menu.createMenu(menuDto1);

            MenuDto menuDto2 = new MenuDto();
            menuDto2.setName("코스B");
            menuDto2.setPrice(95000);
            menuDto2.setDescription("해산물2 + 사이드 2");
            menuDto2.setMenuType(MenuType.SPECIAL);
            Menu special2 = Menu.createMenu(menuDto2);

            MenuDto menuDto3 = new MenuDto();
            menuDto3.setName("코스C");
            menuDto3.setPrice(110000);
            menuDto3.setDescription("스테이크2 + 버거2 + 사이드 1 + 음료4");
            menuDto3.setMenuType(MenuType.SPECIAL);
            Menu special3 = Menu.createMenu(menuDto3);

            MenuDto menuDto4 = new MenuDto();
            menuDto4.setName("스테이크");
            menuDto4.setPrice(25000);
            menuDto4.setDescription("스테이크 입니다");
            menuDto4.setMenuType(MenuType.GENERAL);
            Menu general1 = Menu.createMenu(menuDto4);

            MenuDto menuDto5 = new MenuDto();
            menuDto5.setName("버거");
            menuDto5.setPrice(16000);
            menuDto5.setDescription("수제버거 입니다");
            menuDto5.setMenuType(MenuType.GENERAL);
            Menu general2 = Menu.createMenu(menuDto5);

            Menu specialA = menuRepository.save(special1);
            Menu specialB = menuRepository.save(special2);
            Menu specialC = menuRepository.save(special3);
            Menu generalA = menuRepository.save(general1);
            Menu generalB = menuRepository.save(general2);

            /** OrderMenu */
            /** A */
//            OrderMenuDto orderMenuDto1 = OrderMenuDto.builder().menuId(specialA.getId()).count(1).build();
            OrderMenuDto orderMenuDto1 = OrderMenuDto.builder().menuId(specialA.getId()).count(3).build();
            OrderMenuDto orderMenuDto2 = OrderMenuDto.builder().menuId(specialA.getId()).count(5).build();
            OrderMenuDto orderMenuDto3 = OrderMenuDto.builder().menuId(specialA.getId()).count(6).build();

            /** B */
            OrderMenuDto orderMenuDto4 = OrderMenuDto.builder().menuId(specialB.getId()).count(2).build();
            OrderMenuDto orderMenuDto5 = OrderMenuDto.builder().menuId(specialB.getId()).count(4).build();
            OrderMenuDto orderMenuDto6 = OrderMenuDto.builder().menuId(specialB.getId()).count(6).build();

            /** C */
            OrderMenuDto orderMenuDto7 = OrderMenuDto.builder().menuId(specialC.getId()).count(3).build();
            OrderMenuDto orderMenuDto8 = OrderMenuDto.builder().menuId(specialC.getId()).count(4).build();
            OrderMenuDto orderMenuDto9 = OrderMenuDto.builder().menuId(specialC.getId()).count(5).build();


            /** */

            List<OrderMenuDto> orderMenuDtoList1= new ArrayList<>();
            orderMenuDtoList1.add(orderMenuDto1);
            orderMenuDtoList1.add(orderMenuDto4);
            orderMenuDtoList1.add(orderMenuDto8);

            List<OrderMenuDto> orderMenuDtoList2= new ArrayList<>();
            orderMenuDtoList2.add(orderMenuDto1);
            orderMenuDtoList2.add(orderMenuDto5);


            List<OrderMenuDto> orderMenuDtoList3= new ArrayList<>();
            orderMenuDtoList3.add(orderMenuDto6);
            orderMenuDtoList3.add(orderMenuDto7);

            List<OrderMenuDto> orderMenuDtoList4= new ArrayList<>();
            orderMenuDtoList4.add(orderMenuDto3);
            orderMenuDtoList4.add(orderMenuDto9);
            orderMenuDtoList4.add(orderMenuDto4);

            List<OrderMenuDto> orderMenuDtoList5= new ArrayList<>();
            orderMenuDtoList5.add(orderMenuDto2);
            orderMenuDtoList5.add(orderMenuDto6);
            orderMenuDtoList5.add(orderMenuDto7);

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

        public void plus_Member_Reservation() {
            /** date */
            LocalDate date9 = LocalDate.of(2023, 9, 11);
            LocalTime time9 = LocalTime.of(13, 0, 0);

            LocalDate date10 = LocalDate.of(2023, 9, 11);
            LocalTime time10 = LocalTime.of(16, 0, 0);

            LocalDate date11 = LocalDate.of(2023, 9, 11);
            LocalTime time11 = LocalTime.of(19, 0, 0);

            LocalDate date12 = LocalDate.of(2023, 10, 14);
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

            LocalDate date19 = LocalDate.of(2023, 11, 11);
            LocalTime time19 = LocalTime.of(12, 0, 0);

            LocalDate date20 = LocalDate.of(2023, 11, 20);
            LocalTime time20 = LocalTime.of(15, 0, 0);


            /** reservation */
            List<ReservationDto> reservationList = new ArrayList<>();
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
            reservationList.add(reservationDto9);reservationList.add(reservationDto10);
            reservationList.add(reservationDto11);reservationList.add(reservationDto12);
            reservationList.add(reservationDto13);reservationList.add(reservationDto14);
            reservationList.add(reservationDto15);reservationList.add(reservationDto16);
            reservationList.add(reservationDto17);reservationList.add(reservationDto18);
            reservationList.add(reservationDto19);reservationList.add(reservationDto20);

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

                Member findMember = memberRepository.save(member);

                int twelve= i%12;
                reservationService.addReservation(findMember.getId(), reservationList.get(twelve));
            }
        }

    }
}
