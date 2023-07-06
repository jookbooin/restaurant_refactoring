package com.restaurant.reservation.web.form;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
public class LoginForm {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;
}
