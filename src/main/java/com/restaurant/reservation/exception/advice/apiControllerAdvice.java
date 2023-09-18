package com.restaurant.reservation.exception.advice;


import com.restaurant.reservation.exception.BadRequestException;
import com.restaurant.reservation.exception.CategoryException;
import com.restaurant.reservation.exception.MenuException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "com.restaurant.reservation.api.controller")
public class apiControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<ErrorResult> CategoryHandler(CategoryException e) {
        log.error("[exceptionHandler] ex", e);
        ErrorResult errorResult = new ErrorResult("카테고리 관련 오류", e.getMessage());
        return new ResponseEntity(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResult> MenuHandler(MenuException e) {
        log.error("[exceptionHandler] ex", e);
        ErrorResult errorResult = new ErrorResult("메뉴 관련 오류", e.getMessage());
        return new ResponseEntity(errorResult, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler
    public ResponseEntity<ErrorResult> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();

        ErrorResponse errorResult = ErrorResponse.create(bindingResult);
        return new ResponseEntity(errorResult, HttpStatus.BAD_REQUEST);
    }

    /** 400 에러 */
    @ExceptionHandler
    public ResponseEntity<ErrorResult> BadRequestExceptionHandler(BadRequestException e) {
        log.error("[exceptionHandler] ex", e);
        ErrorResult errorResult = new ErrorResult("400", e.getMessage());
        return new ResponseEntity(errorResult, HttpStatus.BAD_REQUEST);
    }

    /** 500 에러 */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult exHandler(Exception e) {
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult("500", e.getMessage());
    }

}
