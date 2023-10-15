package com.restaurant.reservation.controller;

import com.restaurant.reservation.api.request.search.MemberSearchRequest;
import com.restaurant.reservation.domain.members.Member;
import com.restaurant.reservation.repository.MemberRepository;
import com.restaurant.reservation.repository.dto.BookingSearch;
import com.restaurant.reservation.repository.dto.BookingSearchDto;
import com.restaurant.reservation.repository.dto.MemberSearch;
import com.restaurant.reservation.service.BookingWebService;
import com.restaurant.reservation.service.MemberService;
import com.restaurant.reservation.web.webDto.BookingSearchWeb;
import com.restaurant.reservation.web.webDto.MemberWeb;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AdminController {

    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final BookingWebService bookingWebService;

    @GetMapping("/admin/member/list")
    public String adminMemberList(Model model, @ModelAttribute("memberSearch") MemberSearchRequest condition, @PageableDefault(page = 0, size = 5) Pageable pageable) {

        log.info("MemberSearchCondition : {}", condition);
        log.info("pageable : {}", pageable);

        MemberSearch searchCondition = MemberSearch.createSearchCondition(condition);
        Page<Member> memberPage = memberService.findMemberAll(searchCondition, pageable);
        Page<MemberWeb> dtoPage = memberPage.map(member -> MemberWeb.webFrom(member));

        List<MemberWeb> dtoList = dtoPage.getContent();

        model.addAttribute("dtoPage", dtoPage);

        return "basic/admin/adminMemberList";
    }

    @GetMapping("/admin/advance/list")
    public String adminAdvanceList(Model model, @ModelAttribute("bookingSearch") BookingSearchWeb bookingSearchWeb, BindingResult bindingResult, @PageableDefault(page = 0, size = 10) Pageable pageable) {

        log.info("bookingSearchWeb : {}",bookingSearchWeb);
        log.info("pageable : {}",pageable);

        /** startDate , EndDate 뒤바뀌었을때 */
        if(bookingSearchWeb.getStartDate() != null && bookingSearchWeb.getEndDate() != null)
            if(bookingSearchWeb.getStartDate().isAfter(bookingSearchWeb.getEndDate())){
                bindingResult.rejectValue("startDate", "date.After");
//                return "basic/admin/adminAdvanceList";
            }

        /** number 가 올바른 */
        if(StringUtils.hasText(bookingSearchWeb.getSearchType()))
            if(bookingSearchWeb.getSearchType().equals("number")){
                try {
                    int IntegerKeyword = Integer.parseInt(bookingSearchWeb.getKeyword().trim());
                } catch (NumberFormatException e) {
    //                return "basic/admin/adminAdvanceList";
                    bindingResult.rejectValue("keyword", "typeMismatch.java.lang.Integer");
                }
            }

        if (bindingResult.hasErrors()){
            log.info("errors={}", bindingResult);
            return "basic/admin/adminAdvanceList";
        }

        BookingSearch bookingSearch = BookingSearch.TransformWeb(bookingSearchWeb);
        log.info("bookingSearch : {}",bookingSearch);
        log.info("=== Before ==");
        Page<BookingSearchDto> dtoPage = bookingWebService.findAllAdvanceReservation(bookingSearch, pageable);
        log.info("=== After ===");
        log.info("totalPage : {}",dtoPage.getTotalPages());
        log.info("totalElements : {}",dtoPage.getTotalElements());
        log.info("numberOfElements : {}",dtoPage.getNumberOfElements());
        log.info("number : {}",dtoPage.getNumber());

        model.addAttribute("dtoPage", dtoPage);


        return "basic/admin/adminAdvanceList";
    }




}

