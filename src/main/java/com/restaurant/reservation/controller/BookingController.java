package com.restaurant.reservation.controller;

import com.restaurant.reservation.domain.OrderMenu;
import com.restaurant.reservation.domain.booking.Reservation;
import com.restaurant.reservation.repository.ReservationRepository;
import com.restaurant.reservation.service.ReservationService;
import com.restaurant.reservation.web.SessionID;
import com.restaurant.reservation.web.webDto.BookingWebDto;
import com.restaurant.reservation.web.webDto.OrderMenuWebDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BookingController {

    /** Booking : 조회 메서드 위주일듯
     *            Reservation
     *            Waitinh
     *            */
    private final ReservationRepository reservationRepository;
    private final ReservationService reservationService;

    /** 예약조회 */
    @GetMapping("/booking/advance")
    public String ReservationAdvance(Model model, HttpSession session){
        Long sessionId = (Long) session.getAttribute(SessionID.LOGIN_MEMBER);
        List<Reservation> reservationPreList = reservationRepository.findReservationPre(sessionId);

        /** reservation -> dto로 변환하여 PrePage로 반환 */
        List<BookingWebDto> webDtoList = reservationPreList.stream()
                .map(o -> BookingWebDto.createDto(o))
                .collect(Collectors.toList());

        model.addAttribute("bookingList",webDtoList);
        return "basic/members/booking/bookingAdvance";
    }

    @GetMapping("/booking/history/complete")
    public String ReservationHistoryComplete(Model model,HttpSession session){
        Long sessionId = (Long) session.getAttribute(SessionID.LOGIN_MEMBER);
        List<Reservation> reservationCompleteList = reservationRepository.findReservationComplete(sessionId);

        /** reservation -> dto로 변환하여 CompletePage로 반환 */
        List<BookingWebDto> webDtoList = reservationCompleteList.stream()
                .map(o -> BookingWebDto.createDto(o))
                .collect(Collectors.toList());

        model.addAttribute("bookingList",webDtoList);
        return "basic/members/booking/bookingComplete";
    }


    @GetMapping("/booking/history/noshow")
    public String ReservationHistoryNoShow(Model model,HttpSession session){
        Long sessionId = (Long) session.getAttribute(SessionID.LOGIN_MEMBER);
        List<Reservation> reservationNoShowList = reservationRepository.findReservationNoShow(sessionId);

        /** reservation -> dto로 변환하여 NoShowPage로 반환 */
        List<BookingWebDto> webDtoList = reservationNoShowList.stream()
                .map(o -> BookingWebDto.createDto(o))
                .collect(Collectors.toList());

        model.addAttribute("bookingList",webDtoList);
        return "basic/members/booking/bookingNoShow";
    }

    @ResponseBody
    @GetMapping("/booking/{rid}/orderMenuList")
    public ModalMenu bookOrderMenuList(@PathVariable("rid") Long id,HttpSession session){
        try {
            log.info("확인");
            List<OrderMenu> orderMenuList = getOrderMenuList(id);
            List<OrderMenuWebDto> orderList = orderMenuList.stream()
                    .map(o -> OrderMenuWebDto.createWebDto(o))
                    .collect(Collectors.toList());

            int sum = orderList.stream().mapToInt(o->o.getTotalPrice()).sum();
            log.info("확인");
            return new ModalMenu(sum,orderList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private List<OrderMenu> getOrderMenuList(Long id) {
        return reservationService.findOrderMenuList(id);
    }
    @Data
    @AllArgsConstructor
    static class ModalMenu<T> {
        private int sum;
        private T data;
    }

}
