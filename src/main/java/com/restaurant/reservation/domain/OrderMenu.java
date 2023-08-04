package com.restaurant.reservation.domain;

import com.restaurant.reservation.domain.booking.Reservation;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@Entity
public class OrderMenu {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_menu_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;
    private int orderPrice;    // 할인이 포함된 가격일 수 있음 totalPrice랑 구별하는게 좋을듯?
    private int count;

    public OrderMenu(Menu menu, int orderMenuPrice, int count) {
        this.menu = menu;
        this.orderPrice = orderMenuPrice;
        this.count = count;
    }

    public OrderMenu() {
    }

    // OrderMenu 생성 
    public static OrderMenu createOrderMenu(Menu menu, int count) {
        return new OrderMenu(menu, menu.getPrice(),count );
    }
    // 개수 변경 -> orderPrice 변경
    public void changeCount(int count){
        this.count=count;
    }
    
    // OrderMenu 가격 총합 계산용
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }

    public void addReservation(Reservation reservation){
        this.reservation = reservation;
    }
}
