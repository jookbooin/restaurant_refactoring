package com.restaurant.reservation.domain.members;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@DiscriminatorValue("customers")
public class Customer extends Member {

    @Embedded
    private MemberInfo memberInfo;
}
