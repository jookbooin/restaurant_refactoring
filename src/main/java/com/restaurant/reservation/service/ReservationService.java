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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReservationService {

    private final MemberRepository memberRepository;
    private final MenuRepository menuRepository;
    private final ReservationRepository reservationRepository;


    @Transactional
    public Reservation addReservation(Long memberId , LocalDate date, int number, LocalTime time , List<OrderMenuDto> orderMenuDtoList){

        // 엔티티 조회
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("can't find Member"));

        // 주문 상품 리스트 생성
        // 1. 주문 상품 생성 -> OrderMenuDto - MenuId, count 담김
        // 2. 리스트로 만들어야 함
        List<OrderMenu> orderMenuList = new ArrayList<>();
        for (OrderMenuDto orderMenuDto : orderMenuDtoList) {
            Menu findMenu = menuRepository.findById(orderMenuDto.getMenuId()).orElseThrow(() -> new RuntimeException("can't find Menu"));
            OrderMenu createOrderMenu = OrderMenu.createOrderMenu(findMenu, orderMenuDto.getCount());
            orderMenuList.add(createOrderMenu);
        }

        Reservation createReservation = Reservation.createReservation(member,date,number,time,orderMenuList);
        Reservation reservation = reservationRepository.save(createReservation);
        return reservation;
    }

    @Transactional
    public void deleteReservation(Long id ) {
//        Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new RuntimeException("reservation delete reservation : " + id));

        Optional<Reservation> reservationOpt = reservationRepository.findById(id);
        reservationOpt.ifPresent(findReservation ->reservationRepository.delete(findReservation) );
    }

    @Transactional
    public void updateReservation(Long id , ReservationDto reservationDto){
        /** 리스트는 어떻게 update 하는 것이지? */
        Optional<Reservation> reservationOpt = reservationRepository.findById(id);
        reservationOpt.ifPresent(findReservation ->findReservation.updateReservation(reservationDto));

    }


}
