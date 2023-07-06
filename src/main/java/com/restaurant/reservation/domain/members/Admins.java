package com.restaurant.reservation.domain.members;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@DiscriminatorValue("admins")
public class Admins extends Member {

}
