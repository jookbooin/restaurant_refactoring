package com.restaurant.reservation.domain.dto;

import com.restaurant.reservation.domain.enumType.MemberType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberDto {
    private Long id;
    private String email;
    private String password;
    private String name;
    private String phoneNumber;

    private MemberType memberType;

}
