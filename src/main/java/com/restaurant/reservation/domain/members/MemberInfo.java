package com.restaurant.reservation.domain.members;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@ToString
public class MemberInfo {
    private String name;
    private String phoneNumber;

    public MemberInfo(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public MemberInfo() {

    }
}
