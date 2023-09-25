package com.restaurant.reservation.api.response;

import com.restaurant.reservation.domain.members.Member;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.format.DateTimeFormatter;

@ToString
@Setter
@Getter
public class MemberResponse {
    private Long id ;
    private String name;
    private String number;
    private String email;
    private String grade;
    private String createdDateTime;
    private String modifiedDateTime;

    public static MemberResponse createDto(Member member){
        MemberResponse apiDto = new MemberResponse();
        apiDto.setId(member.getId());
        apiDto.setName(member.getMemberInfo().getName());
        apiDto.setNumber(member.getMemberInfo().getPhoneNumber());
        apiDto.setEmail(member.getEmail());

        apiDto.setGrade(member.getMemberGrade().getName());
        /** String 으로 변환 */
        DateTimeFormatter transferToString = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String createdDateTime = member.getCreatedDate().format(transferToString);
        String modifiedDateTime = member.getModifiedDate().format(transferToString);
        apiDto.setCreatedDateTime(createdDateTime);
        apiDto.setModifiedDateTime(modifiedDateTime);
        return apiDto;
    }

}
