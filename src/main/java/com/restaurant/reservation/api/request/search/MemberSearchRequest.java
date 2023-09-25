package com.restaurant.reservation.api.request.search;

import lombok.*;

import java.util.ArrayList;
import java.util.List;


@ToString
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberSearchRequest {

    private String searchType;  // 이름 : name
    private String keyword; //  %고객%
    private List<String> grades = new ArrayList<>();   // 브론즈 , 실버 , 골드 -> BRONZE , SILVER , GOLD 이 선택되어 올 수도?

    public MemberSearchRequest(String searchType, String keyword, List<String> grades) {
        this.searchType = searchType;
        this.keyword = keyword;
        this.grades = grades;
    }
}
