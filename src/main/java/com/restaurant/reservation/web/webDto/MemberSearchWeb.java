package com.restaurant.reservation.web.webDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@Setter
public class MemberSearchWeb {

    private String searchType;  // 이름 : name
    private String keyword; //  %고객%
    private List<String> grades = new ArrayList<>();   // 브론즈 , 실버 , 골드 -> BRONZE , SILVER , GOLD 이 선택되어 올 수도?

    public MemberSearchWeb() {
    }

    public MemberSearchWeb(String searchType, String keyword, List<String> grades) {
        this.searchType = searchType;
        this.keyword = keyword;
        this.grades = grades;
    }
}
