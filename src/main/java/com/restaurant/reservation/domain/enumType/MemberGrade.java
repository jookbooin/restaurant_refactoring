package com.restaurant.reservation.domain.enumType;

import lombok.Getter;

@Getter
public enum MemberGrade {
    BRONZE("브론즈"),SILVER("실버"),GOLD("골드");

    private final String grade;

    MemberGrade(String grade) {
        this.grade = grade;
    }

}
