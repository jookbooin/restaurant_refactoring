package com.restaurant.reservation.exception.errorCode;

import org.springframework.http.HttpStatus;


public enum CommonErrorCode implements ErrorCode{

    
    INVALID_PARAMETER(400,HttpStatus.BAD_REQUEST, "Invalid parameter included"),
   NOT_FOUND(404,HttpStatus.NOT_FOUND, "not found"),
    INTERNAL_SERVER_ERROR(500,HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");
    private final int status;
    private final HttpStatus httpStatus;
    private final String message;

    CommonErrorCode(int status, HttpStatus httpStatus, String message) {
        this.status = status;
        this.httpStatus = httpStatus;
        this.message = message;
    }

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
