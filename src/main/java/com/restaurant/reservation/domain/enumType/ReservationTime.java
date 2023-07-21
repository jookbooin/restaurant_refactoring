package com.restaurant.reservation.domain.enumType;

import java.time.LocalTime;


public enum ReservationTime {
    TEN("오전 10:00","10:00"), ELEVEN("오전 11:00","11:00"),
    TWELVE("오후 12:00","12:00"), THIRTEEN("오후 1:00","13:00"), FOURTEEN("오후 2:00","14:00"),
    FIFTHTTEN("오후 3:00","15:00"), SIXTEEN("오후 4:00","16:00"), SEVENTEEN("오후 5:00","17:00"),
    EIGHTTEEN("오후 6:00","18:00"), NINETEEN("오후 7:00","19:00"), TWENTY("오후 8:00","20:00");

    private final String time;
    private final String name;

    ReservationTime(String name, String time) {
        this.name=name;
        this.time=time;
    }

    public LocalTime transferTime(String time){
        LocalTime localTime = LocalTime.parse(time);
        return localTime;
    }

    public String getTime() {
        return time;
    }

    public String getName() {
        return name;
    }
}
