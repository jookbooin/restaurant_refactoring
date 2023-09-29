package com.restaurant.reservation.common.exception;

import com.restaurant.reservation.common.exception.errorCode.ErrorCode;
import lombok.Getter;

@Getter
public class RestApiException extends RuntimeException{
    private final ErrorCode errorCode;

    public RestApiException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

}
