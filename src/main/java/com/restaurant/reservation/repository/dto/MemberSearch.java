package com.restaurant.reservation.repository.dto;

import com.restaurant.reservation.domain.enumType.MemberGrade;
import com.restaurant.reservation.web.webDto.MemberSearchWeb;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class MemberSearch {

    private String searchType;  // 이름 : name
    private String keyword; //  %고객%
    private List<MemberGrade> grades = new ArrayList<>();   // 브론즈 , 실버 , 골드 -> BRONZE , SILVER , GOLD 이 선택되어 올 수도?

    public MemberSearch() {
    }

    public MemberSearch(String searchType, String keyword, List<MemberGrade> grades) {
        this.searchType = searchType;
        this.keyword = keyword;
        this.grades = grades;
    }

    public static MemberSearch createSearchCondition(MemberSearchWeb apiCondition){
        MemberSearch condition = new MemberSearch();
        condition.setSearchType(apiCondition.getSearchType());
        condition.setKeyword(apiCondition.getKeyword());
        for (String gradeString : apiCondition.getGrades()) {
            condition.getGrades().add(MemberGrade.valueOf(gradeString));
        }
        return condition;
    }
}
