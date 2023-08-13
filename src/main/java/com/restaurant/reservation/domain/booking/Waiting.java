package com.restaurant.reservation.domain.booking;

import com.restaurant.reservation.domain.enumType.MemberRole;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Setter
@Getter
@Entity
@DiscriminatorValue("waiting")
@EntityListeners(AuditingEntityListener.class)
public class Waiting extends Booking{

    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;
}
