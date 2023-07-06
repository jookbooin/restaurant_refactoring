package com.restaurant.reservation.domain.members;

import com.restaurant.reservation.domain.TimeEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "members")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="dtype")
public abstract class Member extends TimeEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    private String email;
    @Column(name = "password")
    private String pw;

//    @Column(name="type")
//    private MemberType memberType;

}