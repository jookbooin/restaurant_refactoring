package com.restaurant.reservation.exception.advice;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.FieldError;

@Setter
@Getter

public class FieldErrorDetail {

    private String field;
    private String code;
    private String value;
    private String message;

    @Builder
    public FieldErrorDetail(String field, String code, String value, String message) {
        this.field = field;
        this.code = code;
        this.value = value;
        this.message = message;
    }

    public static FieldErrorDetail create(FieldError fieldError) {
        return FieldErrorDetail.builder()
                .field(fieldError.getField())
                .value(fieldError.getRejectedValue()==null?null:fieldError.getRejectedValue().toString())
                .code(fieldError.getCode())
                .message(fieldError.getDefaultMessage())
                .build();
    }
}
