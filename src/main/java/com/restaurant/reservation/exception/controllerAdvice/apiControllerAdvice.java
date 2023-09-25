package com.restaurant.reservation.exception.controllerAdvice;


import com.restaurant.reservation.exception.domain.DomainException;
import com.restaurant.reservation.exception.errorCode.CommonErrorCode;
import com.restaurant.reservation.exception.errorCode.DomainErrorCode;
import com.restaurant.reservation.exception.errorCode.ErrorCode;
import com.restaurant.reservation.exception.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class apiControllerAdvice extends ResponseEntityExceptionHandler {


    /** @Override
     *  DefaultHandlerExceptionResolver : spring 내부 예외 처리
     *  ResponseEntityExceptionHandler : DefaultHandlerExceptionResolver + @ExceptionHandler
     * */
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.warn("[handleNoHandlerFoundException]", ex);
        ErrorCode errorCode = CommonErrorCode.NOT_FOUND;
        return handleExceptionInternal(errorCode,ex);
    }
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.warn("[handleMethodArgumentNotValid]", ex);
        ErrorCode errorCode = CommonErrorCode.INVALID_PARAMETER;
        return handleExceptionInternal(errorCode,ex);
    }




    /** domain 관련해서 발생하는 에러 */
    @ExceptionHandler
    public ResponseEntity<Object> DomainExceptionHandler(DomainException ex) {
        log.error("[DomainException] ", ex);

        ErrorCode errorCode = DomainErrorCode.DOMAIN_ERROR;
        return handleExceptionInternal(errorCode, ex.getMessage());
    }

    /** 400 에러 */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> IllegalArgumentExceptionHandler(IllegalArgumentException ex) {
        log.warn("[IllegalArgumentException]", ex);
        ErrorCode errorCode = CommonErrorCode.INVALID_PARAMETER;
        return handleExceptionInternal(errorCode, ex.getMessage());
    }

    /**
     * 500 에러
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ResponseEntity<Object> exHandler(Exception ex) {

        log.error("[Exception] ", ex);
        ErrorCode errorCode = CommonErrorCode.INTERNAL_SERVER_ERROR;
        return handleExceptionInternal(errorCode, ex.getMessage());
    }

    private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode, NoHandlerFoundException errors) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(createErrorResponse(errorCode,errors));
    }
    /** bindingResult */
    private ResponseEntity<Object> handleExceptionInternal( ErrorCode errorCode, BindException errors) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(createErrorResponse(errorCode,errors));
    }


    private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode, String message) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(createErrorResponse(errorCode, message));
    }

    private ErrorResponse createErrorResponse(ErrorCode errorCode, NoHandlerFoundException errors) {
        StringBuilder sb = new StringBuilder();
        sb.append(errors.getHttpMethod())
                .append(" ")
                .append(errorCode.getMessage());

        return ErrorResponse.builder()
                .status(errorCode.getStatus())
                .code(errorCode.name())
                .message(sb.toString())
                .build();
    }

    private ErrorResponse createErrorResponse( ErrorCode errorCode ,BindException e) {
        BindingResult errors = e.getBindingResult();
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

    private ErrorResponse createErrorResponse(ErrorCode errorCode) {
        return ErrorResponse.builder()
                .status(errorCode.getStatus())
                .code(errorCode.name())
                .message(errorCode.getMessage())
                .build();
    }

}
