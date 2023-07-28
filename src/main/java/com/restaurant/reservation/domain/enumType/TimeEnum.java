package com.restaurant.reservation.domain.enumType;

import lombok.Getter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


@Getter
public enum TimeEnum {   // abstract class  -> enum 추상클래스다 .
    TEN("오전 10:00"),

    // 하위에 선언된 열거형 변수는 실제로 변수가 아니라 enum타입 상속받은 하위클래스다. ==  열거형 상수 하나하나가  Time Enum 객체이다.
    // 외부에 노출되어 있고, 생성할 필요가 없으며 , 런타임에 교체가 불가능하다. -> public static final 타입
    // ->static  final TimeEnum TEN = new TimeEnum("TEN");

    //
    ELEVEN("오전 11:00"),
    TWELVE("오후 12:00"), THIRTEEN("오후 1:00"), FOURTEEN("오후 2:00"),
    FIFTHTTEN("오후 3:00"), SIXTEEN("오후 4:00"), SEVENTEEN("오후 5:00"),
    EIGHTTEEN("오후 6:00"), NINETEEN("오후 7:00"), TWENTY("오후 8:00");

    private final String time;


    private TimeEnum(String time) {  // 생성자가 있어도 객체를 생성할 수 없다 -> 생성자의 제어자가 private 이기 때문에
        this.time=time;
    }

    public LocalTime transferTime(String time){
        LocalTime localTime = LocalTime.parse(time);
        return localTime;
    }

    public static LocalTime transferStringToTime(String enumTime){
        DateTimeFormatter transferToLocalTime = DateTimeFormatter.ofPattern("a h:mm");
        LocalTime formatTime = LocalTime.parse(enumTime, transferToLocalTime);
        return formatTime;
    }
    public static String transferTimeToString(LocalTime localTime){
        DateTimeFormatter transferToString = DateTimeFormatter.ofPattern("a h:mm");
        String formatString = localTime.format(transferToString);
        return formatString;
    }


}
