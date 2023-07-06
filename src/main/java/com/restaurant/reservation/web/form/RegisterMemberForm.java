package com.restaurant.reservation.web.form;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;

@Getter
@Setter
public class RegisterMemberForm {
    @NotBlank
    @Email(message = "이메일 형식을 맞춰주세요.")
    private String email;
    @NotBlank
//    @Size(min = 8 , max = 20)
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String pwd;
    @NotNull
    private String pwdCheck;
    @NotBlank
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,10}$", message = "이름은 특수문자를 제외한 2~10자리여야 합니다.")
    private String name;
    @NotBlank
    private String phoneNumber;

}
