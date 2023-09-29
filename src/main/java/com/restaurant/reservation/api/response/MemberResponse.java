package com.restaurant.reservation.api.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.restaurant.reservation.domain.members.Member;
import lombok.*;

import java.time.LocalDateTime;

@ToString
@Setter
@Getter
@NoArgsConstructor
public class MemberResponse {
    private Long id ;
    private String name;
    private String number;
    private String email;
    private String grade;
    @JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDateTime;
    @JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedDateTime;

    @Builder
    public MemberResponse(Long id, String name, String number, String email, String grade, LocalDateTime createdDateTime, LocalDateTime modifiedDateTime) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.email = email;
        this.grade = grade;
        this.createdDateTime = createdDateTime;
        this.modifiedDateTime = modifiedDateTime;
    }

    public static MemberResponse responseFrom(Member member) {
        return MemberResponse.builder()
                .id(member.getId())
                .name(member.getMemberInfo().getName())
                .number(member.getMemberInfo().getPhoneNumber())
                .email(member.getMemberInfo().getPhoneNumber())
                .grade(member.getMemberGrade().getName())
                .createdDateTime(member.getCreatedDate())
                .modifiedDateTime(member.getModifiedDate())
                .build();
    }
//        MemberResponse apiDto = new MemberResponse();
//        apiDto.setId(member.getId());
//        apiDto.setName(member.getMemberInfo().getName());
//        apiDto.setNumber(member.getMemberInfo().getPhoneNumber());
//        apiDto.setEmail(member.getEmail());
//
//        apiDto.setGrade(member.getMemberGrade().getName());
//        /** String 으로 변환 */
//        DateTimeFormatter transferToString = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        String createdDateTime = member.getCreatedDate().format(transferToString);
//        String modifiedDateTime = member.getModifiedDate().format(transferToString);
//        apiDto.setCreatedDateTime(createdDateTime);
//        apiDto.setModifiedDateTime(modifiedDateTime);
//        return apiDto;

}
