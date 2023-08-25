package com.restaurant.reservation.repository.dto;

import com.restaurant.reservation.domain.enumType.MemberRole;
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

    private MemberRole memberRole;

}
