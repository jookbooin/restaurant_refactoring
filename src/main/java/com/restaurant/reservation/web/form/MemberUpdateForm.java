package com.restaurant.reservation.web.form;

import com.restaurant.reservation.repository.dto.MemberDto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class MemberUpdateForm {
    private Long id;
    @NotBlank
    @Email(message = "이메일 형식을 맞춰주세요.")
    private String email;
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}" ,message = "비밀번호는 8~16자 영문 소문자, 숫자, 특수문자를 사용하세요.")
    private String password;
    @NotNull
    private String passwordCheck;
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,10}$", message = "이름은 특수문자를 제외한 2~10자리여야 합니다.")
    private String name;
    @NotBlank
    private String phoneNumber;

    public MemberUpdateForm(Long id , String email, String password, String name, String phoneNumber) {
        this.id=id;
        this.email = email;
        this.password = password;
        this.passwordCheck = passwordCheck;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public static MemberUpdateForm makeForm(MemberDto memberDto){
        return new MemberUpdateForm(memberDto.getId(),
                             memberDto.getEmail(),
                                memberDto.getPassword(),
                                memberDto.getName(),
                                 memberDto.getPhoneNumber());
    }
}
