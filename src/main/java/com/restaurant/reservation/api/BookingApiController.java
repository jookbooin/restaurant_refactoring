package com.restaurant.reservation.api;

import com.restaurant.reservation.api.dto.MenuCountApiDto;
import com.restaurant.reservation.api.dto.OrderMenuApiDto;
import com.restaurant.reservation.api.dto.ReservationApiDto;
import com.restaurant.reservation.api.request.UpdateReservationRequest;
import com.restaurant.reservation.domain.OrderMenu;
import com.restaurant.reservation.domain.booking.Reservation;
import com.restaurant.reservation.repository.dto.MenuDto;
import com.restaurant.reservation.repository.dto.ReservationDto;
import com.restaurant.reservation.repository.ReservationRepository;
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

    @GetMapping("/api/booking/{rid}/orderMenuList")
    public ModalMenuResult bookOrderMenuList(@PathVariable("rid") Long rid, HttpSession session){
        try {
            List<OrderMenu> orderMenuList = getOrderMenuList(rid);
            List<OrderMenuApiDto> orderList = orderMenuList.stream()
                    .map(o -> OrderMenuApiDto.createWebDto(o))
                    .collect(Collectors.toList());

            orderList.forEach(System.out::println);
            int sum = orderList.stream().mapToInt(o->o.getTotalPrice()).sum();
            System.out.println("sum = " + sum);
            return new ModalMenuResult(sum,orderList);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/api/booking/advance/list")
    public oneListResult bookingAdvanceList(HttpSession session){

        Long sessionId = (Long) session.getAttribute(SessionID.LOGIN_MEMBER);
//        Long sessionId = 1L;
        List<Reservation> reservationAdvanceList = reservationRepository.findReservationAdvance(sessionId);

        List<ReservationApiDto> apiDtoList = reservationAdvanceList.stream()
                .map(o -> ReservationApiDto.createApiDto(o))
                .collect(Collectors.toList());

        apiDtoList.forEach(System.out::println);

        return new oneListResult(apiDtoList);
    }

    @GetMapping("/api/booking/complete/list")
    public oneListResult bookingCompleteList(HttpSession session){

        Long sessionId = (Long) session.getAttribute(SessionID.LOGIN_MEMBER);
//        Long sessionId = 1L;
        List<Reservation> reservationCompleteList = reservationRepository.findReservationComplete(sessionId);

        List<ReservationApiDto> apiDtoList = reservationCompleteList.stream()
                .map(o -> ReservationApiDto.createApiDto(o))
                .collect(Collectors.toList());

        apiDtoList.forEach(System.out::println);

        return new oneListResult(apiDtoList);
    }

    @GetMapping("/api/booking/noshow/list")
    public oneListResult bookingNoShowList(HttpSession session){

        Long sessionId = (Long) session.getAttribute(SessionID.LOGIN_MEMBER);
//        Long sessionId = 1L;
        List<Reservation> reservationNoShowList = reservationRepository.findReservationNoShow(sessionId);

        List<ReservationApiDto> apiDtoList = reservationNoShowList.stream()
                .map(o -> ReservationApiDto.createApiDto(o))
                .collect(Collectors.toList());

        apiDtoList.forEach(System.out::println);

        return new oneListResult(apiDtoList);
    }
    @DeleteMapping("/api/booking/{rid}/delete")
    public ResponseEntity<String> deleteBooking(@PathVariable("rid") Long rid , HttpSession session ){
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
        List<MenuDto> specialMenuList = menuService.findSpecialMenu();
        List<OrderMenu> orderMenuList = getOrderMenuList(rid);

        List<MenuCountApiDto> orderMenuDtoList = orderMenuList.stream()
                .map(o -> MenuCountApiDto.MenuApiDtoFromOrderMenu(o))
                .collect(Collectors.toList());

        return new twoListResult(specialMenuList,orderMenuDtoList);
    }

    @PatchMapping("/api/booking/{rid}/modify")
    public ResponseEntity<String> modifyReservation(@RequestBody UpdateReservationRequest updateReservationRequest, HttpSession session ){

        System.out.println("updateReservationRequest = " + updateReservationRequest);

        if( updateReservationRequest.getNumber() > updateReservationRequest.MenuCount()){
            return new ResponseEntity<>("인원수 만큼 메뉴 선택을 해야합니다. ",HttpStatus.BAD_REQUEST);
        }

        ReservationDto reservationDto = ReservationDto.requestToDto(updateReservationRequest);
        System.out.println("reservationDto = " + reservationDto);

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
