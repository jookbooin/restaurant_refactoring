package com.restaurant.reservation.exception.controllerAdvice;


import com.restaurant.reservation.exception.domain.DomainException;
import com.restaurant.reservation.exception.errorCode.CommonErrorCode;
import com.restaurant.reservation.exception.errorCode.DomainErrorCode;
import com.restaurant.reservation.exception.errorCode.ErrorCode;
import com.restaurant.reservation.exception.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "com.restaurant.reservation.api.controller")
public class apiControllerAdvice{

    @ExceptionHandler
    public ResponseEntity<Object> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        ErrorCode errorCode = CommonErrorCode.METHOD_ARGUMENT_NOT_VALID;
        return handleExceptionInternal(errorCode,bindingResult);
    }

    /** 400 에러 */
    @ExceptionHandler
    public ResponseEntity<Object> DomainExceptionHandler(DomainException e) {
        log.error("[DomainException] ", e);

        ErrorCode errorCode = DomainErrorCode.DOMAIN_ERROR;
        return handleExceptionInternal(errorCode, e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> IllegalArgumentExceptionHandler(IllegalArgumentException e) {
        log.warn("[IllegalArgumentException]", e);
        ErrorCode errorCode = CommonErrorCode.INVALID_PARAMETER;
        return handleExceptionInternal(errorCode, e.getMessage());
    }

    /**
     * 500 에러
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ResponseEntity<Object> exHandler(Exception e) {

        log.error("[Exception] ", e);
        ErrorCode errorCode = CommonErrorCode.INTERNAL_SERVER_ERROR;
        return handleExceptionInternal(errorCode, e.getMessage());
    }

    private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode, String message) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(createErrorResponse(errorCode, message));
    }

    private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode, Errors errors) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(createErrorResponse(errorCode, errors));
    }

    private ErrorResponse createErrorResponse(ErrorCode errorCode, Errors errors) {
        return ErrorResponse.builder()
                .status(errorCode.getStatus())
                .code(errorCode.name())
                .message(errorCode.getMessage())
                .errors(ErrorResponse.getFieldErrorList(errors))
                .build();
    }
    private ErrorResponse createErrorResponse(ErrorCode errorCode, String message) {
        return ErrorResponse.builder()
                .status(errorCode.getStatus())
                .code(errorCode.name())
                .message(message)
                .build();
    }

    private ErrorResponse create(ErrorCode errorCode) {
        return ErrorResponse.builder()
                .status(errorCode.getStatus())
                .code(errorCode.name())
                .message(errorCode.getMessage())
                .build();
    }

}
