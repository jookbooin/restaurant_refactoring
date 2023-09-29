package com.restaurant.reservation.repository.dto;

import com.restaurant.reservation.api.request.search.MemberSearchRequest;
import com.restaurant.reservation.domain.enumType.MemberGrade;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
public class MemberSearch {

    private String searchType;  // 이름 : name
    private String keyword; //  %고객%
    private List<MemberGrade> grades = new ArrayList<>();   // 브론즈 , 실버 , 골드 -> BRONZE , SILVER , GOLD 이 선택되어 올 수도?

    public MemberSearch() {
    }

    @Builder
    public MemberSearch(String searchType, String keyword, List<MemberGrade> grades) {
        this.searchType = searchType;
        this.keyword = keyword;
        this.grades = grades;
    }

    public static MemberSearch createSearchCondition(MemberSearchRequest request){
        List<MemberGrade> gradeList = null;
        if (request.getGrades().size() != 0) {
            gradeList = request.getGrades().stream()
                    .map(MemberGrade::valueOf)
                    .collect(Collectors.toList());
        }else{
            gradeList = new ArrayList<>();
        }

        return MemberSearch.builder()
                .searchType(request.getSearchType())
                .keyword(request.getKeyword())
                .grades(gradeList)
                .build();
    }
}
