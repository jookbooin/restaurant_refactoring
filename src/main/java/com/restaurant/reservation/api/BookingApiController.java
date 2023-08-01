package com.restaurant.reservation.api;

import com.restaurant.reservation.api.dto.ReservationApiDto;
import com.restaurant.reservation.domain.OrderMenu;
import com.restaurant.reservation.domain.booking.Reservation;
import com.restaurant.reservation.repository.ReservationRepository;
import com.restaurant.reservation.service.ReservationService;
import com.restaurant.reservation.web.SessionID;
import com.restaurant.reservation.web.webDto.OrderMenuWebDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BookingApiController {

    private final ReservationRepository reservationRepository;
    private final ReservationService reservationService;

    @GetMapping("/booking/{rid}/orderMenuList")
    public ModalMenuResult bookOrderMenuList(@PathVariable("rid") Long id, HttpSession session){
        try {
            List<OrderMenu> orderMenuList = getOrderMenuList(id);
            List<OrderMenuWebDto> orderList = orderMenuList.stream()
                    .map(o -> OrderMenuWebDto.createWebDto(o))
                    .collect(Collectors.toList());

            orderList.forEach(System.out::println);
            int sum = orderList.stream().mapToInt(o->o.getTotalPrice()).sum();

            return new ModalMenuResult(sum,orderList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/booking/list")
    public ListResult bookingList(HttpSession session){

        Long sessionId = (Long) session.getAttribute(SessionID.LOGIN_MEMBER);
//        Long sessionId = 1L;
        List<Reservation> reservationAdvanceList = reservationRepository.findReservationAdvance(sessionId);

        List<ReservationApiDto> apiDtoList = reservationAdvanceList.stream()
                .map(o -> ReservationApiDto.createApiDto(o))
                .collect(Collectors.toList());

        apiDtoList.forEach(System.out::println);

        return new ListResult(apiDtoList);
    }
    @DeleteMapping("/booking/{rid}/delete")
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
    static class ListResult<T> {

        private T data;
    }

}
