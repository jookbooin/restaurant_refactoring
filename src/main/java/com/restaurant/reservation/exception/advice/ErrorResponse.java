package com.restaurant.reservation.exception.advice;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.Errors;

import java.util.List;
import java.util.stream.Collectors;
@Setter
@Getter
public class ErrorResponse {

    private String objectName;
    private List<FieldErrorDetail> errors;

    @Builder
    public ErrorResponse(String objectName, List<FieldErrorDetail> errors) {
        this.objectName = objectName;
        this.errors = errors;
    }

    public static ErrorResponse create(Errors errors) {
        List<FieldErrorDetail> details =
                errors.getFieldErrors()
                        .stream()
                        .map(error -> FieldErrorDetail.create(error))
                        .collect(Collectors.toList());

        return ErrorResponse.builder()
                .objectName(errors.getObjectName())
                .errors(details).build();

    }
}
