package com.restaurant.reservation.domain.dto;

import com.restaurant.reservation.domain.enumType.MemberType;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Builder
public class SessionDto {
    private Long id;
    private String name;
    private MemberType memberType;
}

