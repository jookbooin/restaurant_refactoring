package com.restaurant.reservation.api.controller;

import com.restaurant.reservation.api.request.UpdateReservationRequest;
import com.restaurant.reservation.api.response.MenuCountResponse;
import com.restaurant.reservation.api.response.MenuResponse;
import com.restaurant.reservation.api.response.OrderMenuApiDto;
import com.restaurant.reservation.api.response.ReservationApiDto;
import com.restaurant.reservation.domain.CategoryMenu;
import com.restaurant.reservation.domain.OrderMenu;
import com.restaurant.reservation.domain.booking.Reservation;
import com.restaurant.reservation.repository.ReservationRepository;
import com.restaurant.reservation.repository.dto.ReservationDto;
import com.restaurant.reservation.service.CategoryMenuService;
import com.restaurant.reservation.service.CategoryService;
import com.restaurant.reservation.service.MenuService;
import com.restaurant.reservation.service.ReservationService;
import com.restaurant.reservation.web.SessionID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BookingApiController {

    private final MenuService menuService;
    private final ReservationRepository reservationRepository;
    private final ReservationService reservationService;
    private final CategoryService categoryService;
    private final CategoryMenuService categoryMenuService;



    @GetMapping("/api/booking/advance/list")
    public oneListResult bookingAdvanceList(HttpSession session){
        log.info("/api/booking/advance/list");
        log.info("api 검색?");

        Long sessionId = (Long) session.getAttribute(SessionID.LOGIN_MEMBER);
//        Long sessionId = 1L;
        List<Reservation> reservationAdvanceList = reservationRepository.findReservationAdvance(sessionId);

        List<ReservationApiDto> apiDtoList = reservationAdvanceList.stream()
                .map(o -> ReservationApiDto.createApiDto(o))
                .collect(Collectors.toList());

        apiDtoList.forEach(dto -> log.info(dto.toString()));

        return new oneListResult(apiDtoList);
    }

    @GetMapping("/api/booking/complete/list")
    public oneListResult bookingCompleteList(HttpSession session){

        log.info("/api/booking/complete/list");
        Long sessionId = (Long) session.getAttribute(SessionID.LOGIN_MEMBER);
//        Long sessionId = 1L;
        List<Reservation> reservationCompleteList = reservationRepository.findReservationComplete(sessionId);

        List<ReservationApiDto> apiDtoList = reservationCompleteList.stream()
                .map(o -> ReservationApiDto.createApiDto(o))
                .collect(Collectors.toList());

        apiDtoList.forEach(dto -> log.info(dto.toString()));

        return new oneListResult(apiDtoList);
    }

    @GetMapping("/api/booking/noshow/list")
    public oneListResult bookingNoShowList(HttpSession session){

        log.info("/api/booking/noshow/list");
        Long sessionId = (Long) session.getAttribute(SessionID.LOGIN_MEMBER);
//        Long sessionId = 1L;
        List<Reservation> reservationNoShowList = reservationRepository.findReservationNoShow(sessionId);

        List<ReservationApiDto> apiDtoList = reservationNoShowList.stream()
                .map(o -> ReservationApiDto.createApiDto(o))
                .collect(Collectors.toList());

        apiDtoList.forEach(dto -> log.info(dto.toString()));

        return new oneListResult(apiDtoList);
    }

    @GetMapping("/api/booking/{rid}/orderMenuList")
    public ModalMenuResult bookOrderMenuList(@PathVariable("rid") Long rid, HttpSession session){
        log.info("/api/booking/{}/orderMenuList",rid);
        try {
            List<OrderMenu> orderMenuList = getOrderMenuList(rid);
            List<OrderMenuApiDto> orderList = orderMenuList.stream()
                    .map(o -> OrderMenuApiDto.createWebDto(o))
                    .collect(Collectors.toList());

            orderList.forEach(order -> log.info(order.toString()));
            int sum = orderList.stream().mapToInt(o->o.getTotalPrice()).sum();
            log.info("sum : " + sum);
            return new ModalMenuResult(sum,orderList);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @DeleteMapping("/api/booking/{rid}/delete")
    public ResponseEntity<String> deleteBooking(@PathVariable("rid") Long rid , HttpSession session ){
        log.info("/api/booking/{}/delete",rid);
        try {
            Long sessionId = 1L;
//            Long sessionId = (Long) session.getAttribute(SessionID.LOGIN_MEMBER);
            int rowCount = reservationService.deleteReservation(rid, sessionId);
            if(rowCount != 1)
                throw new Exception("삭제 실패");
            return new ResponseEntity<>("삭제했습니다.",HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("삭제 실패했습니다..",HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/api/booking/{rid}/orderMenuList/specialMenu")
    public twoListResult getMenuOrderMenuList(@PathVariable("rid") Long rid){
        log.info("/api/booking/{}/orderMenuList/specialMenu",rid);
        final String categoryNameSpecial = "스페셜";

        String categoryCode = categoryService.findCode(categoryNameSpecial);
        List<CategoryMenu> categoryMenuList = categoryMenuService.findCategoryMenu(categoryCode);
        List<MenuResponse> menuList = categoryMenuList.stream().map(cm -> MenuResponse.of(cm))
                .collect(Collectors.toList());


        List<OrderMenu> orderMenuList = getOrderMenuList(rid);
        List<MenuCountResponse> orderMenuDtoList = orderMenuList.stream()
                .map(o -> MenuCountResponse.of(o))
                .collect(Collectors.toList());

        return new twoListResult(menuList,orderMenuDtoList);
    }

    @PatchMapping("/api/booking/{rid}/modify")
    public ResponseEntity<String> modifyReservation(@RequestBody UpdateReservationRequest updateReservationRequest, HttpSession session ){

        log.info("updateReservationRequest : {} ",updateReservationRequest );

        if( updateReservationRequest.getNumber() > updateReservationRequest.MenuCount()){
            return new ResponseEntity<>("인원수 만큼 메뉴 선택을 해야합니다. ",HttpStatus.BAD_REQUEST);
        }

        ReservationDto reservationDto = ReservationDto.requestToDto(updateReservationRequest);
        log.info("reservationDto : {}",reservationDto);

        Long sessionId = (Long) session.getAttribute(SessionID.LOGIN_MEMBER);

        int cnt = reservationService.updateReservation(reservationDto, sessionId);
        if (cnt == 1)
            return new ResponseEntity<>("예약 수정되었습니다.",HttpStatus.OK);

        return new ResponseEntity<>("시스템 오류오류오류오류!!!",HttpStatus.BAD_REQUEST);
    }


    @Data
    @AllArgsConstructor
    static class oneListResult<T> {
        private T data;
    }


    private List<OrderMenu> getOrderMenuList(Long id) {
        return reservationService.findOrderMenuList(id);
    }
    @Data
    @AllArgsConstructor
    static class ModalMenuResult<T> {
        private int sum;
        private T data;
    }



    @Data
    @AllArgsConstructor
    static class twoListResult<T> {
        private T list1;
        private T list2;
    }

}
