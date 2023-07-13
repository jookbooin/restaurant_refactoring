package com.restaurant.reservation.domain.booking;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Setter
@Getter
@Entity
@DiscriminatorValue("waiting")
public class Waiting extends Booking{



}
