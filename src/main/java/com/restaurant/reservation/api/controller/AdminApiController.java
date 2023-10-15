package com.restaurant.reservation.api.controller;

import com.restaurant.reservation.api.request.search.BookingSearchRequest;
import com.restaurant.reservation.api.response.MemberResponse;
import com.restaurant.reservation.domain.enumType.MemberRole;
import com.restaurant.reservation.domain.members.Member;
import com.restaurant.reservation.repository.MemberRepository;
import com.restaurant.reservation.repository.ReservationRepository;
import com.restaurant.reservation.repository.dto.BookingSearch;
import com.restaurant.reservation.repository.dto.BookingSearchDto;
import com.restaurant.reservation.repository.dto.MemberSearch;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AdminApiController {

    private final MemberRepository memberRepository;
    private final ReservationRepository reservationRepository;
    @GetMapping("/api/admin/member/list")
    public Page<MemberResponse> memberManageList(@PageableDefault(page = 0,sort = "id") Pageable pageable){

        /**
         * api/admin/member/manage?size=10&page=1 처럼 전달해 줄 수 있는 듯
         * */
        log.info("pageable : {}",pageable.toString());
        Page<Member> memberPage = memberRepository.findAllByMemberRole(MemberRole.CUSTOMER, pageable);

        Page<MemberResponse> responsePage = memberPage.map(member -> MemberResponse.responseFrom(member));

        return responsePage;
    }

    @GetMapping("/api/admin/member/search")
    public Page<MemberResponse> memberSearchList(MemberSearch condition , @PageableDefault(page = 0,size = 5) Pageable pageable){
        log.info("condition : {}",condition.getSearchType());
        log.info("condition : {}",condition.getKeyword());

//        for (MemberGrade grade : condition.getGrades()) {
//            log.info("grade : {}",grade);
//            log.info("type : {}",grade.getClass());
//        }
        Page<Member> result = memberRepository.findMemberSearchCondition(condition, pageable);
        for (Member member : result) {
            System.out.println("member = " + member);
        }
        Page<MemberResponse> responsePage = result.map(member -> MemberResponse.responseFrom(member));

        return responsePage;
    }


    /** api는 Jackson 라이브러리 써야한다고 한다. -> 임의로 java로 parsing함.*/
    @GetMapping("/api/admin/advance/list")
    public Page<BookingSearchDto> advanceList(BookingSearchRequest bookingSearchRequest, @PageableDefault(page = 0,size = 10)Pageable pageable){

        log.info("bookingSearchRequest : {}",bookingSearchRequest);
        log.info("pageable : {}",pageable);
        BookingSearch bookingSearch = BookingSearch.TransformApi(bookingSearchRequest);
        log.info("bookingSearch : {}",bookingSearch);
        log.info("=== Before ==");

        Page<BookingSearchDto> result = reservationRepository.findAllAdvanceReservation(bookingSearch, pageable);

        log.info("=== After ===");
        log.info("totalPage : {}",result.getTotalPages());
        log.info("totalElements : {}",result.getTotalElements());
        log.info("numberOfElements : {}",result.getNumberOfElements());
        log.info("number : {}",result.getNumber());
        return result;
    }

}
