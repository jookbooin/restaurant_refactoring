package com.restaurant.reservation.exception.errorCode;

import org.springframework.http.HttpStatus;

public interface ErrorCode {

    String name();  // enum 클래스 내부적으로 정의 되어 있는 메소드
                    // 열거형 상수의 이름을 문자열로 반환한다.

    int getStatus();

    HttpStatus getHttpStatus();

     String getMessage();
}
