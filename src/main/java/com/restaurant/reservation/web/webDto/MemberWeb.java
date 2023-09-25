package com.restaurant.reservation.web.webDto;

import com.restaurant.reservation.domain.members.Member;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.format.DateTimeFormatter;

@ToString
@Setter
@Getter
public class MemberWeb {
    private Long id ;
    private String name;
    private String number;
    private String email;
    private String grade;
    private String createdDateTime;
    private String modifiedDateTime;

    public static MemberWeb memberFrom(Member member){
        MemberWeb webDto = new MemberWeb();
        webDto.setId(member.getId());
        webDto.setName(member.getMemberInfo().getName());
        webDto.setNumber(member.getMemberInfo().getPhoneNumber());
        webDto.setEmail(member.getEmail());

        webDto.setGrade(member.getMemberGrade().getName());
        /** String 으로 변환 */
        DateTimeFormatter transferToString = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String createdDateTime = member.getCreatedDate().format(transferToString);
        String modifiedDateTime = member.getModifiedDate().format(transferToString);
        webDto.setCreatedDateTime(createdDateTime);
        webDto.setModifiedDateTime(modifiedDateTime);
        return webDto;
    }

}
