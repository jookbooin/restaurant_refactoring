package com.restaurant.reservation.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public abstract class TimeEntity {
    private LocalDateTime createdDate;
    private LocalDateTime latestDate;
}
