package com.restaurant.reservation.web.webDto;

import com.restaurant.reservation.domain.OrderMenu;
import com.restaurant.reservation.domain.booking.Reservation;
import com.restaurant.reservation.domain.enumType.TimeEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/** 사용자 MyPage에 예약 목록을 전달하는 Dto */
@Setter
@Getter
public class MyBookingWebDto {

    /** 0729
     * Pre 조회때 reservation - waiting 문제점
     *
     * 1. Booking 전체 조회 - ( reservation , waiting ) 동시에 조회하는 dto로 생각중임
     *
     * 2. Time을 Booking으로 옮겨야할 수도 있겠음.
     *          waiting : time을 autoEdit을  통해 현재 시간으로 조작한다면 옮길 수 있을 것 같다.
     *
     * 3. ordermenu는 reservation에서만 존재하는 것.
     *                waiting도 이 dto를 사용해도 될까? -> 우선 초기화는 해두었음
     *
     * */

//    private String restaurantName;
    private Long id;
    private int number;
    private String date;
    private String time;
    private int totalPrice;
    private List<OrderMenuWebDto> orderMenuList = new ArrayList<>();


    public static MyBookingWebDto createDto(Reservation reservation){

        MyBookingWebDto webDto = new MyBookingWebDto();
        webDto.setId(reservation.getId());
        webDto.setNumber(reservation.getNumber());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        webDto.setDate(reservation.getDate().format(formatter));
        webDto.setTime(TimeEnum.transferTimeToString(reservation.getTime()));

        /** reservation 일때 -> 메뉴 리스트 추가 */
        if(reservation.getOrderMenus().size() >0){
            int totalPrice = 0;
            for (OrderMenu orderMenu : reservation.getOrderMenus()) {
                OrderMenuWebDto orderMenuWebDto = OrderMenuWebDto.createWebDto(orderMenu);
                totalPrice+=orderMenuWebDto.getOrderPrice();
                webDto.getOrderMenuList().add(orderMenuWebDto);
            }
        webDto.setTotalPrice(totalPrice);
        }

        return webDto;
    }
}
