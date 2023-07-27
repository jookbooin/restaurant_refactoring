package com.restaurant.reservation.controller;

import com.restaurant.reservation.domain.booking.Reservation;
import com.restaurant.reservation.repository.ReservationRepository;
import com.restaurant.reservation.web.SessionID;
import com.restaurant.reservation.web.webDto.BookingWebDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class BookingController {

    /** Booking : 조회 메서드 위주일듯
     *            Reservation
     *            Waitinh
     *            */
    private final ReservationRepository reservationRepository;

    /** 예약조회 */
    @GetMapping("/booking/advance")
    public String ReservationPre(Model model, HttpSession session){
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

}
