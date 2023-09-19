package com.restaurant.reservation.exception.errorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum DomainErrorCode implements ErrorCode {

    DOMAIN_ERROR(400,HttpStatus.BAD_REQUEST, "Domain Error!!");
    private final int status;
    private final HttpStatus httpStatus;
    private final String message;
}
