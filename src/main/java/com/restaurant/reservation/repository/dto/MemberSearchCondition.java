package com.restaurant.reservation.repository.dto;

import com.restaurant.reservation.api.dto.MemberSearchApiCondition;
import com.restaurant.reservation.domain.enumType.MemberGrade;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class MemberSearchCondition {

    private String searchType;  // 이름 : name
    private String keyword; //  %고객%
    private List<MemberGrade> grades = new ArrayList<>();   // 브론즈 , 실버 , 골드 -> BRONZE , SILVER , GOLD 이 선택되어 올 수도?

    public MemberSearchCondition() {
    }

    public MemberSearchCondition(String searchType, String keyword, List<MemberGrade> grades) {
        this.searchType = searchType;
        this.keyword = keyword;
        this.grades = grades;
    }

    public static MemberSearchCondition createSearchCondition(MemberSearchApiCondition apiCondition){
        MemberSearchCondition condition = new MemberSearchCondition();
        condition.setSearchType(apiCondition.getSearchType());
        condition.setKeyword(apiCondition.getKeyword());
        for (String gradeString : apiCondition.getGrades()) {
            condition.getGrades().add(MemberGrade.valueOf(gradeString));
        }
        return condition;
    }
}
