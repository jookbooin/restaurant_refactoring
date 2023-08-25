package com.restaurant.reservation.domain.members;

import com.restaurant.reservation.domain.TimeEntity;
import com.restaurant.reservation.repository.dto.MemberDto;
import com.restaurant.reservation.domain.enumType.MemberGrade;
import com.restaurant.reservation.domain.enumType.MemberRole;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@ToString
@Setter
@Getter
@Entity
@Table(name = "members")
public class Member extends TimeEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    private String email;
    @Column(name = "password")
    private String password;
    @Embedded
    private MemberInfo memberInfo;


    @Column(name="member_grade")
    @Enumerated(EnumType.STRING)
    private MemberGrade memberGrade;
    @Column(name="member_type")
    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;

    public Member() {
    }

    public Member( String email, String password, MemberInfo memberInfo, MemberRole memberRole) {
        this.email = email;
        this.password = password;
        this.memberInfo = memberInfo;
        this.memberRole = memberRole;

    }

    public Member( String email, String password, MemberInfo memberInfo,MemberGrade memberGrade, MemberRole memberRole) {
        this.email = email;
        this.password = password;
        this.memberInfo = memberInfo;
        this.memberGrade=memberGrade;
        this.memberRole = memberRole;

    }



    public static Member createCustomer(MemberDto memberDto){

        MemberInfo info = new MemberInfo(memberDto.getName(), memberDto.getPhoneNumber());

        return new Member(memberDto.getEmail(), memberDto.getPassword(), info ,MemberGrade.BRONZE, MemberRole.CUSTOMER);
    }
    public static Member createAdmin(MemberDto memberDto){

        MemberInfo info = new MemberInfo(memberDto.getName(), memberDto.getPhoneNumber());
        return new Member(memberDto.getEmail(), memberDto.getPassword(), info , MemberRole.ADMIN);
    }

    public void update(String email,String password){
        this.email=email;
        this.password=password;
    }




}