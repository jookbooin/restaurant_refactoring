package com.restaurant.reservation.exception.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.Errors;

import java.util.List;
import java.util.stream.Collectors;
@Setter
@Getter
public class ErrorResponse {
    private int status;
    private String code;
    private String message;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<FieldErrorDetail> errors;

    @Builder
    public ErrorResponse(int status, String code, String message, List<FieldErrorDetail> errors) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.errors = errors;
    }

    public static List<FieldErrorDetail> getFieldErrorList(Errors errors) {

        return errors.getFieldErrors()
                .stream()
                .map(error -> FieldErrorDetail.create(error))
                .collect(Collectors.toList());
    }

    public static ErrorResponse create(Errors errors) {
        List<FieldErrorDetail> details =
                errors.getFieldErrors()
                        .stream()
                        .map(error -> FieldErrorDetail.create(error))
                        .collect(Collectors.toList());

        return ErrorResponse.builder()
                .errors(details).build();

    }
}
