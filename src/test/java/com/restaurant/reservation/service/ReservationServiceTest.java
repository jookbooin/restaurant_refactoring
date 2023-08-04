package com.restaurant.reservation.service;

import com.restaurant.reservation.domain.Menu;
import com.restaurant.reservation.domain.OrderMenu;
import com.restaurant.reservation.domain.booking.Reservation;
import com.restaurant.reservation.domain.dto.OrderMenuDto;
import com.restaurant.reservation.domain.dto.ReservationDto;
import com.restaurant.reservation.domain.members.Member;
import com.restaurant.reservation.repository.MemberRepository;
import com.restaurant.reservation.repository.MenuRepository;
import com.restaurant.reservation.repository.ReservationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
class ReservationServiceTest {

    @Autowired
    EntityManager em;
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MenuRepository menuRepository;

    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    ReservationService reservationService;

    @Test
    @Rollback(false)
//    @Transactional
    public void 등록_삭제Test() throws Exception{

        // given
        Member findmember = memberRepository.findByEmail("3670lsh@naver.com").get();
        LocalDate todayDate = LocalDate.of(2023, 7, 12);
        LocalTime todayTime = LocalTime.of(23, 30, 0);
        // when
        Menu menu1 = menuRepository.findById(1L).get();
        Menu menu2 = menuRepository.findById(2L).get();
        Menu menu3 = menuRepository.findById(3L).get();

        List<OrderMenuDto> orderMenuDtoList = new ArrayList<>();
        OrderMenuDto orderMenuDto1 = OrderMenuDto.builder().menuId(1L).count(3).build();
        OrderMenuDto orderMenuDto2 = OrderMenuDto.builder().menuId(2L).count(4).build();
        OrderMenuDto orderMenuDto3 = OrderMenuDto.builder().menuId(3L).count(5).build();
        orderMenuDtoList.add(orderMenuDto1);
        orderMenuDtoList.add(orderMenuDto2);
        orderMenuDtoList.add(orderMenuDto3);

        ReservationDto reservationDto1 = ReservationDto.builder()
                .date(todayDate).time(todayTime).number(4).orderMenuList(orderMenuDtoList).build();
        ReservationDto reservationDto2 = ReservationDto.builder()
                .date(todayDate).time(todayTime).number(4).orderMenuList(orderMenuDtoList).build();

        Reservation reservation1 = reservationService.addReservation(1L, reservationDto1);
        Assertions.assertEquals(reservation1.getId(),1L);
        Reservation reservation2 = reservationService.addReservation(2L, reservationDto2);
        Assertions.assertEquals(reservation2.getId(),2L);

        /***/

        /**삭제 test */
//        System.out.println("=== Reservation1 삭제 ===");
//        reservationService.deleteReservation(1L);  // transaction 이후에
//
//        System.out.println("=== Reservation2 삭제 ===");
//        reservationService.deleteReservation(2L); // transaction 이후에
    }

    @Test
    @Transactional
    public void findClass() throws Exception{
        Member findmember = memberRepository.findByEmail("3670lsh@naver.com").get();
        LocalDate todayDate = LocalDate.of(2023, 7, 12);
        LocalTime todayTime = LocalTime.of(23, 30, 0);
        // when
        Menu menu1 = menuRepository.findById(1L).get();
        Menu menu2 = menuRepository.findById(2L).get();
        Menu menu3 = menuRepository.findById(3L).get();

        List<OrderMenuDto> orderMenuDtoList = new ArrayList<>();
        OrderMenuDto orderMenuDto1 = OrderMenuDto.builder().menuId(1L).count(3).build();
        OrderMenuDto orderMenuDto2 = OrderMenuDto.builder().menuId(2L).count(4).build();
        OrderMenuDto orderMenuDto3 = OrderMenuDto.builder().menuId(3L).count(5).build();
        orderMenuDtoList.add(orderMenuDto1);
        orderMenuDtoList.add(orderMenuDto2);
        orderMenuDtoList.add(orderMenuDto3);

        ReservationDto reservationDto1 = ReservationDto.builder()
                .date(todayDate).time(todayTime).number(4).orderMenuList(orderMenuDtoList).build();
        ReservationDto reservationDto2 = ReservationDto.builder()
                .date(todayDate).time(todayTime).number(4).orderMenuList(orderMenuDtoList).build();

        Reservation reservation1 = reservationService.addReservation(1L, reservationDto1);
        Assertions.assertEquals(reservation1.getId(),1L);
        Reservation reservation2 = reservationService.addReservation(2L, reservationDto2);
        Assertions.assertEquals(reservation2.getId(),2L);

        Reservation findReservation = reservationRepository.findById(1L).get();
        System.out.println("findReservation.getClass() = " + findReservation.getClass());
        System.out.println("findReservation.getOrderMenus().getClass() = " + findReservation.getOrderMenus().get(0).getClass());
    }

    @Test
    @Rollback(false)
    public void updateReservation() throws Exception{
        /** 1. 메뉴 종류 바꿀수 있는지
         *  2. 메뉴의 개수도 바꿀 수 있는지 */

        Member findmember = memberRepository.findByEmail("3670lsh@naver.com").get();
        LocalDate todayDate = LocalDate.of(2023, 7, 12);
        LocalTime todayTime = LocalTime.of(23, 30, 0);

        Menu menu1 = menuRepository.findById(1L).get();
        Menu menu2 = menuRepository.findById(2L).get();
//        Menu menu3 = menuRepository.findById(3L).get();
//
        List<OrderMenuDto> orderMenuDtoList = new ArrayList<>();
        OrderMenuDto orderMenuDto1 = OrderMenuDto.builder().menuId(1L).count(3).build();
        OrderMenuDto orderMenuDto2 = OrderMenuDto.builder().menuId(2L).count(4).build();
//        OrderMenuDto orderMenuDto3 = OrderMenuDto.builder().menuId(3L).count(5).build();
        orderMenuDtoList.add(orderMenuDto1);
        orderMenuDtoList.add(orderMenuDto2);
//        orderMenuDtoList.add(orderMenuDto3);

        ReservationDto reservationDto1 = ReservationDto.builder()
                .date(todayDate).time(todayTime).number(4).orderMenuList(orderMenuDtoList).build();

//
        Reservation reservation1 = reservationService.addReservation(1L, reservationDto1);
        Assertions.assertEquals(reservation1.getId(),1L);
//        Reservation reservation2 = reservationService.addReservation(2L, todayDate, 4, todayTime, orderMenuDtoList);
//        Assertions.assertEquals(reservation2.getId(),2L);

        System.out.println("==처음==");
        for (OrderMenu orderMenu : reservation1.getOrderMenus()) {
            System.out.println("orderMenu = " + orderMenu);
        }

        // 개수 변경된 list
        List<OrderMenuDto> modifyOrderMenuDtoList = new ArrayList<>();
//        modifyOrderMenuDtoList.add(new OrderMenuDto(1L,3)); // 개수 동일
        modifyOrderMenuDtoList.add(new OrderMenuDto(2L,2)); // 개수 변경

        // remove 이후 -> id가 변하지 않는 menu만 존재
        // count가 변한다면??
        // id 확인 -> count 다른 것 stream으로 뽑아야하나???
//        modifyOrderMenuDtoList.forEach(orderMenuDto -> {
//            reservation1.getOrderMenus().stream()
//                    .filter(orderMenu -> orderMenu.getMenu().getId() == orderMenuDto.getMenuId())
//                    .findFirst()
//                    .ifPresent(orderMenu -> orderMenu.changeCount(orderMenuDto.getCount()));
//        });

        modifyOrderMenuDtoList.forEach(orderMenuDto -> {
            for (OrderMenu orderMenu : reservation1.getOrderMenus()) {
                if(orderMenu.getMenu().getId() == orderMenuDto.getMenuId()){
                    orderMenu.changeCount(orderMenuDto.getCount());         // 메소드로 필드 접근 -> 변경감지
                    break;
                }
            }
        });


        System.out.println("==1회 실행==");
        for (OrderMenu orderMenu : reservation1.getOrderMenus()) {
            System.out.println("orderMenu = " + orderMenu);
        }


        /** 1. Id 리스트인 List<Long> 로 요소 삭제 */
//        List<Long> removeIdList = List.of(1L,3L);
//        reservation1.getOrderMenus().removeIf(orderMenu -> removeIdList.contains(orderMenu.getMenu().getId()));

        /** 2. OrderMenuDto 리스트로 삭제 가능? */
//        List<OrderMenuDto> removeOrderMenuDtoList = new ArrayList<>();
//        removeOrderMenuDtoList.add(new OrderMenuDto(1L,2));
//        removeOrderMenuDtoList.add(new OrderMenuDto(3L,3));
//
//        reservation1.getOrderMenus().removeIf(orderMenu -> {
//            for (OrderMenuDto orderMenuDto : removeOrderMenuDtoList) {
//                if(orderMenuDto.getMenuId() == orderMenu.getMenu().getId())
//                    return true;
//            }
//            return false;
//        });

        /** 1. 변경감지로 리스트 요소 추가 */
//        List<OrderMenuDto> addOrderMenuDtoList = new ArrayList<>();
//        addOrderMenuDtoList.add(new OrderMenuDto(4L,2));
//        addOrderMenuDtoList.add(new OrderMenuDto(3L,5));
//
//        addOrderMenuDtoList.forEach(orderMenuDto ->{
//            Menu menu = menuRepository.findById(orderMenuDto.getMenuId()).orElseThrow(() -> new RuntimeException("can't find Menu"));
//            OrderMenu orderMenu = OrderMenu.createOrderMenu(menu, orderMenuDto.getCount());
//            reservation1.addOrderMenu(orderMenu);
//        });
         /***/

//        System.out.println("== 입력 ==");
//        System.out.println("Time() = " + reservation1.getTime());
//        System.out.println("Date() = " + reservation1.getDate());
//        System.out.println("Number() = " + reservation1.getNumber());
//
//        System.out.println("== 변경 ==");
//        LocalDate modifyDate = LocalDate.of(2023, 8, 6);
//        LocalTime modifyTime = LocalTime.of(14, 0, 0);

//        System.out.println("==대상 리스트==");
//        reservationRepository.findById(1L).get().getOrderMenus()
//                .stream().forEach(OrderMenu-> System.out.println(OrderMenu.getMenu().getId()));
//        /** form으로 전달해 올때 dto로 전달해 오기때문에 이렇게 설정 */
//        List<OrderMenuDto> modifyList = new ArrayList<>();
//        modifyList.add(orderMenuDto1);
//        modifyList.add(orderMenuDto2);

//        System.out.println("==modifyList 출력==");
//        modifyList.stream().forEach(dto-> {
//            System.out.println(dto.toString());
//        });
//
//        System.out.println("==modifyIdList 출력 ==");
//        List<Long> modifyIdList = modifyList.stream()
//                                            .map(OrderMenuDto::getMenuId)
//                                            .collect(Collectors.toList());
//
//        modifyIdList.stream().forEach(System.out::println);

//        System.out.println("==제거==");
//        Reservation findReservation = reservationRepository.findById(1L).get();
//        List<OrderMenu> orderMenuList = findReservation.getOrderMenus();
//        orderMenuList.remove(0);
//        orderMenuList.stream().forEach(System.out::println);
//        System.out.println("==이전 리스트에서 id를포함하고 있지 않은 객체==");
//        List<OrderMenu> removeList = orderMenuList.stream().filter(orderMenu ->
//                        !modifyIdList.contains(orderMenu.getMenu().getId()))
//                .collect(Collectors.toList());
//        removeList.stream().forEach(orderMenu -> System.out.println(orderMenu.getMenu().getId()));
//        orderMenuList.removeIf(b->removeList.contains(b));
//
//        System.out.println("==최종==");
//        orderMenuList.stream().forEach(orderMenu -> System.out.println(orderMenu.getId()));



/**
 *      Order order = em.find(Order.class, 4L);
 *      List<OrderItem> orderItems = order.getOrderItems();
 *      List<Long> newOrderLineItemId = Arrays.asList(9L);
 * //when
 *      List<OrderItem> removeOrderLine = orderItems.stream()
 *         .filter(orderItem -> !newOrderLineItemId.contains(orderItem.getItem().getId()))
 *         .collect(Collectors.toList());
 *      orderItems.removeIf(removeOrderLine::contains);
 * */

///////////////////////////////////////////////////////////////////////////////////////////////
//        ReservationDto reservationDto = ReservationDto.builder()
//                .number(8)
//                .date(modifyDate)
//                .time(modifyTime)
//                .build();
//
//        reservationService.updateReservation(reservation1.getId(), reservationDto);


//        Assertions.assertEquals(reservation1.getNumber(),8);
    }


    @Test
    @Rollback(false)
    public void 전체삭제후_전체새로등록Test() throws Exception{
        Member findmember = memberRepository.findByEmail("3670lsh@naver.com").get();
        LocalDate todayDate = LocalDate.of(2023, 7, 12);
        LocalTime todayTime = LocalTime.of(23, 30, 0);

        Menu menu1 = menuRepository.findById(1L).get(); // 1
        Menu menu2 = menuRepository.findById(2L).get(); // 2

        List<OrderMenuDto> orderMenuDtoList = new ArrayList<>();
        OrderMenuDto orderMenuDto1 = OrderMenuDto.builder().menuId(1L).count(3).build();
        OrderMenuDto orderMenuDto2 = OrderMenuDto.builder().menuId(2L).count(4).build();

        orderMenuDtoList.add(orderMenuDto1);
        orderMenuDtoList.add(orderMenuDto2);

        ReservationDto reservationDto1 = ReservationDto.builder()
                .date(todayDate).time(todayTime).number(4).orderMenuList(orderMenuDtoList)
                .build();

        Reservation reservation1 = reservationService.addReservation(1L, reservationDto1); // 4
        Assertions.assertEquals(reservation1.getId(),1L);


        /** 시작 */
        Menu menu3 = menuRepository.findById(3L).get(); // 1
        Menu menu4 = menuRepository.findById(4L).get(); // 2

        /** 리스트 생성 */
        List<OrderMenu> modifyList = new ArrayList<>();
        OrderMenu addMenu1 = OrderMenu.createOrderMenu(menu3, 3);
        OrderMenu addMenu2 = OrderMenu.createOrderMenu(menu4, 3);
        modifyList.add(addMenu1);
        modifyList.add(addMenu2);

        System.out.println("==처음실행==");
        for (OrderMenu orderMenu : reservation1.getOrderMenus()) {
            System.out.println("orderMenu = " + orderMenu);
        }

        reservation1.getOrderMenus().clear();


        for (OrderMenu orderMenu : modifyList) {
            reservation1.addOrderMenu(orderMenu);
        }

        System.out.println("==1회 실행==");
        for (OrderMenu orderMenu : reservation1.getOrderMenus()) {
            System.out.println("orderMenu = " + orderMenu);
        }


        /** 1. 전체 수정 */
//        reservation1.getOrderMenus().clear();
//
//        for (OrderMenu orderMenu : modifyList) {
//            reservation1.addOrderMenu(orderMenu);
//        }

    }
    @Test
    @Rollback(false)
    public void 전체삭제후_새로등록_ServiceTest() throws Exception{
        Member findmember = memberRepository.findByEmail("3670lsh@naver.com").get();
        LocalDate todayDate = LocalDate.of(2023, 7, 12);
        LocalTime todayTime = LocalTime.of(23, 30, 0);

        Menu menu1 = menuRepository.findById(1L).get(); // 1
        Menu menu2 = menuRepository.findById(2L).get(); // 2

        List<OrderMenuDto> orderMenuDtoList = new ArrayList<>();
        OrderMenuDto orderMenuDto1 = OrderMenuDto.builder().menuId(1L).count(3).build();
        OrderMenuDto orderMenuDto2 = OrderMenuDto.builder().menuId(2L).count(4).build();

        orderMenuDtoList.add(orderMenuDto1);
        orderMenuDtoList.add(orderMenuDto2);

        ReservationDto reservationDto1 = ReservationDto.builder()
                .date(todayDate).time(todayTime).number(4).orderMenuList(orderMenuDtoList)
                .build();


        Reservation reservation1 = reservationService.addReservation(1L, reservationDto1); // 4
        Assertions.assertEquals(reservation1.getId(),1L);

        em.flush();
        em.clear();


        /** 변경사항 : 인원수 , 날짜 , 시간 */

        LocalDate modifyDate = LocalDate.of(2023, 8, 6);
        LocalTime modifyTime = LocalTime.of(14, 0, 0);

        ReservationDto reservationDto = ReservationDto.builder()
                .number(8)
                .date(modifyDate)
                .time(modifyTime)
                .build();

        /** 수정할 주문리스트 목록  1,2 -> 3,4*/
        List<OrderMenuDto> modifyDtoList = new ArrayList<>();
        OrderMenuDto orderMenuDto3 = OrderMenuDto.builder().menuId(3L).count(1).build();
        OrderMenuDto orderMenuDto4 = OrderMenuDto.builder().menuId(4L).count(1).build();
        modifyDtoList.add(orderMenuDto3);
        modifyDtoList.add(orderMenuDto4);

        /** Service Test */
        System.out.println("======================");
        reservationService.updateReservation_allList(1L,reservationDto,modifyDtoList);

        Reservation assertReservation = reservationRepository.findById(1L).get();
        Assertions.assertEquals(assertReservation.getNumber(),8);
        Assertions.assertEquals(assertReservation.getDate(),modifyDate);
        Assertions.assertEquals(assertReservation.getOrderMenus().get(0).getMenu().getId(),3L);
        Assertions.assertEquals(assertReservation.getOrderMenus().get(1).getMenu().getId(),4L);

        /** collectRemoveList메서드 Test  */
    }

    @Test
    @Rollback(false)
    public void collectRemoveList메서드Test() throws Exception{
        Member findmember = memberRepository.findByEmail("3670lsh@naver.com").get();
        LocalDate todayDate = LocalDate.of(2023, 7, 12);
        LocalTime todayTime = LocalTime.of(23, 30, 0);

        Menu menu1 = menuRepository.findById(1L).get(); // 1
        Menu menu2 = menuRepository.findById(2L).get(); // 2

        List<OrderMenuDto> orderMenuDtoList = new ArrayList<>();
        OrderMenuDto orderMenuDto1 = OrderMenuDto.builder().menuId(1L).count(3).build();
        OrderMenuDto orderMenuDto2 = OrderMenuDto.builder().menuId(2L).count(4).build();

        orderMenuDtoList.add(orderMenuDto1);
        orderMenuDtoList.add(orderMenuDto2);
        ReservationDto reservationDto1 = ReservationDto.builder()
                .date(todayDate).time(todayTime).number(4).orderMenuList(orderMenuDtoList)
                .build();

        Reservation reservation1 = reservationService.addReservation(1L, reservationDto1); // 4
        Assertions.assertEquals(reservation1.getId(),1L);

        em.flush();
        em.clear();

        /** 변경사항 : 인원수 , 날짜 , 시간 */

        LocalDate modifyDate = LocalDate.of(2023, 8, 6);
        LocalTime modifyTime = LocalTime.of(14, 0, 0);

        /** 수정할 주문리스트 목록  1,2 -> 3,4*/
        List<OrderMenuDto> modifyDtoList = new ArrayList<>();
        OrderMenuDto orderMenuDto3 = OrderMenuDto.builder().menuId(1L).count(1).build();
        OrderMenuDto orderMenuDto4 = OrderMenuDto.builder().menuId(4L).count(3).build();
        OrderMenuDto orderMenuDto5 = OrderMenuDto.builder().menuId(3L).count(3).build();
        modifyDtoList.add(orderMenuDto3);
        modifyDtoList.add(orderMenuDto4);
        modifyDtoList.add(orderMenuDto5);

        ReservationDto reservationDto = ReservationDto.builder()
                .rid(1L)
                .number(8)
                .date(modifyDate)
                .time(modifyTime)
                .orderMenuList(modifyDtoList)
                .build();

        /** update 메서드 */
        /** sessionId 넣어주어야 할 것 같음 */
        reservationService.updateReservation(reservationDto,1L);

        Reservation reservation2 = reservationRepository.findById(1L).get();
        Assertions.assertEquals(reservation2.getOrderMenus().size(),3);

        /** public일때 collectRemoveList 테스트*/
//        List<OrderMenu> removeList = reservationService.collectRemoveFromReservation(reservation1, modifyDtoList);
//        Assertions.assertEquals(removeList.size(),1);
//
//        System.out.println();
//        System.out.println("==removeList==");
//        if(!removeList.isEmpty())
//            removeList.forEach(System.out::println);
//
//        System.out.println();
//        System.out.println("==남아있어야 하는 요소==");
//
//        reservationService.deleteRemoveList(reservation1,removeList);
//        Assertions.assertEquals(reservation1.getOrderMenus().size(),1);
//        reservation1.getOrderMenus().forEach(System.out::println);
//
//        System.out.println();
//        System.out.println("==기존 id 변경감지==");
//        List<OrderMenuDto> insertList = reservationService.collectInsertFromModify(reservation1, modifyDtoList);
//        reservation1.getOrderMenus().forEach(System.out::println);
//
//        System.out.println();
//        System.out.println("==insertList==");
//        if(!insertList.isEmpty())
//            insertList.forEach(System.out::println);
//
//        System.out.println();
//        System.out.println("== orderMenu 추가 ==");
//        reservationService.insertOrderMenu(reservation1,insertList);
//        reservation1.getOrderMenus().forEach(System.out::println);
    }

    @Test
    public void updateReservation_메서드_Test() throws Exception{

        // given

        // when

        // then
    }
}