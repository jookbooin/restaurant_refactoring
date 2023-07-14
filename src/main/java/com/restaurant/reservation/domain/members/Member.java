package com.restaurant.reservation.domain.members;

import com.restaurant.reservation.domain.dto.MemberDto;
import com.restaurant.reservation.domain.TimeEntity;
import com.restaurant.reservation.domain.enumType.MemberType;
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
    @Column(name="member_type")
    @Enumerated(EnumType.STRING)
    private MemberType memberType;

    public Member() {
    }

    public Member( String email, String password, MemberInfo memberInfo, MemberType memberType) {
        this.email = email;
        this.password = password;
        this.memberInfo = memberInfo;
        this.memberType = memberType;

    }

    public static Member createCustomer(MemberDto memberDto){

        MemberInfo info = new MemberInfo(memberDto.getName(), memberDto.getPhoneNumber());
        return new Member(memberDto.getEmail(), memberDto.getPassword(), info ,MemberType.CUSTOMER);
    }
    public static Member createAdmin(MemberDto memberDto){

        MemberInfo info = new MemberInfo(memberDto.getName(), memberDto.getPhoneNumber());
        return new Member(memberDto.getEmail(), memberDto.getPassword(), info ,MemberType.ADMIN);
    }

    public void update(String email,String password){
        this.email=email;
        this.password=password;
    }




}