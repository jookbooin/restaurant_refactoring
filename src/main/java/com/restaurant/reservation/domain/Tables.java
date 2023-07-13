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
    public Tables(){

    }
    public Tables (int number, int seats, TableType tableType) {
        this.number = number;
        this.seats = seats;
        this.tableType = tableType;
        this.tableStatus =TableStatus.EMPTY;
    }

    public static Tables createTables(int number, int seats, TableType tableType){
        return new Tables(number,seats,tableType);
    }
}
