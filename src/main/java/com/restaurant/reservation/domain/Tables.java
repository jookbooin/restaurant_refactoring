package com.restaurant.reservation.domain;

import com.restaurant.reservation.domain.enumType.TableStatus;
import com.restaurant.reservation.domain.enumType.TableType;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "tables")
@Getter
public class Tables {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "table_id")
    private Long id;
    @Column(name = "table_number")
    private int number;
    private int seats;
    @Enumerated(EnumType.STRING)
    private TableType tableType;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TableStatus tableStatus;

}
