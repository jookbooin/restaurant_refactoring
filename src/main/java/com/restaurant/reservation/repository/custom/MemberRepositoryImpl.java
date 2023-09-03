package com.restaurant.reservation.repository.custom;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.restaurant.reservation.domain.enumType.MemberGrade;
import com.restaurant.reservation.domain.enumType.MemberRole;
import com.restaurant.reservation.domain.members.Member;
import com.restaurant.reservation.repository.dto.MemberSearch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;

import static com.restaurant.reservation.domain.members.QMember.member;


@Slf4j
public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public MemberRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Member> findMemberSearchCondition(MemberSearch memberSearch, Pageable pageable) {
        /**
         * searchType : name
         * searchInput : % 이승 %
         * grade [] : 브론즈 , 실버
         * */

        log.info("offset:{}",pageable.getOffset());
        log.info("pageSize:{}",pageable.getPageSize());

        List<Member> content = queryFactory.selectFrom(member)
                .where(
                        conditionAndRole(memberSearch.getSearchType(), memberSearch.getKeyword(), MemberRole.CUSTOMER),
                        gradeEq(memberSearch.getGrades())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(member.createdDate.asc())
                .fetch();

        JPAQuery<Member> countQuery = queryFactory
                .selectFrom(member)
                .where(
                        searchCondition(memberSearch.getSearchType(), memberSearch.getKeyword()),
                        gradeEq(memberSearch.getGrades())
                       );

        return PageableExecutionUtils.getPage(content, pageable, () -> countQuery.fetchCount());
    }


    private BooleanExpression gradeEq(List<MemberGrade> grades) {
        if(grades.size()>0){
           return member.memberGrade.in(grades);
        }
        return null;
    }
    private BooleanExpression roleEq(MemberRole memberRole) {
        return member.memberRole.eq(memberRole);
    }

    private BooleanExpression conditionAndRole(String searchType, String keyword,MemberRole memberRole){
        if (searchCondition(searchType,keyword) == null)
            return roleEq(MemberRole.CUSTOMER);
        else
            return searchCondition(searchType,keyword).and(roleEq(MemberRole.CUSTOMER));
    }

    private BooleanExpression searchCondition(String searchType, String keyword) {

        if(StringUtils.hasText(searchType)){

            if(searchType.equals("name")){
                return StringUtils.hasText(keyword) ? member.memberInfo.name.contains(keyword) : null;
            }
            else if (searchType.equals("phoneNumber")){
                return StringUtils.hasText(keyword) ? member.memberInfo.phoneNumber.contains(keyword) : null ;

            }else if (searchType.equals("email")){
                return StringUtils.hasText(keyword) ? member.email.contains(keyword) : null;
            }

        }
        return null;
    }
//    private BooleanBuilder conditionAndRole(String searchType, String keyword,MemberRole memberRole){
//        return searchCondition(searchType,keyword).and(roleEq(MemberRole.CUSTOMER));
//    }
//    private BooleanBuilder searchCondition(String searchType, String keyword) {
//        if(StringUtils.hasText(searchType)){
//            if(keyword.equals("name")){
//               return new BooleanBuilder(member.memberInfo.name.contains(keyword));
//
//            }else if ( keyword.equals("phoneNumber")){
//                return new BooleanBuilder(member.memberInfo.phoneNumber.contains(keyword) );
//
//            }else if(keyword.equals("email")){
//                return new BooleanBuilder(member.email.contains(keyword));
//
//            }
//           return  new BooleanBuilder();
//        }
//        return new BooleanBuilder();
//    }
//
//    private BooleanBuilder roleEq(MemberRole memberRole) {
//        if (memberRole == null) {
//            return new BooleanBuilder();
//        }
//        return new BooleanBuilder(member.memberRole.eq(memberRole));
//    }
//
//    private BooleanBuilder gradeEq(List<MemberGrade> grade) {
//        if(grade.size()>0){
//            return new BooleanBuilder(member.memberGrade.in(grade));
//        }
//        return new BooleanBuilder();
//    }


//    private BooleanBuilder ageEq(Integer age) {
//        return nullSafeBuilder(() -> member.age.eq(age));
//    }
//
//    private BooleanBuilder roleEq(String roleName) {
//        return nullSafeBuilder(() -> member.roleName.eq(roleName));
//    }
//
//    public static BooleanBuilder nullSafeBuilder(Supplier<BooleanExpression> f) {
//        try {
//            return new BooleanBuilder(f.get());
//        } catch (IllegalArgumentException e) {
//            return new BooleanBuilder();
//        }
//    }

}
