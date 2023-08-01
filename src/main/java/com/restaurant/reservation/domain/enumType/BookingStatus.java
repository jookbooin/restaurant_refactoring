package com.restaurant.reservation.domain.enumType;

public enum BookingStatus {
    ADVANCE("사전예약"),COMPLETE("방문"),NOSHOW("노쇼");

    private final String status;


    private BookingStatus(String status) {  // 생성자가 있어도 객체를 생성할 수 없다 -> 생성자의 제어자가 private 이기 때문에
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
