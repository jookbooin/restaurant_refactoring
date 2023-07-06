package com.restaurant.reservation.domain.members;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class MemberInfo {
    private String name;
    private String phoneNumber;

    public MemberInfo(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public MemberInfo() {

    }
    //    @Column(name = "enroll_date")
//    private String date;
}
