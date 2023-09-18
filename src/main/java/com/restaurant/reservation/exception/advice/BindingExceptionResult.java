package com.restaurant.reservation.exception.advice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.Errors;

import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@AllArgsConstructor
public class BindingExceptionResult {
    private List<FieldErrorDetail> errors;

    public static BindingExceptionResult create(Errors errors) {
        List<FieldErrorDetail> details =
                errors.getFieldErrors()
                        .stream()
                        .map(error -> FieldErrorDetail.create(error))
                        .collect(Collectors.toList());
        return new BindingExceptionResult(details);
    }
}
