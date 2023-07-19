package com.restaurant.reservation.controller;


import com.restaurant.reservation.domain.dto.MemberDto;
import com.restaurant.reservation.domain.enumType.MemberType;
import com.restaurant.reservation.domain.members.Member;
import com.restaurant.reservation.service.MemberService;
import com.restaurant.reservation.web.SessionID;
import com.restaurant.reservation.web.form.LoginForm;
import com.restaurant.reservation.web.form.MemberUpdateForm;
import com.restaurant.reservation.web.form.RegisterMemberForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/register")
    public String addForm(@ModelAttribute("member") RegisterMemberForm registerMemberForm) {
        return "basic/registerMemberForm";
    }

    @PostMapping("/register")
    public String save(@Validated @ModelAttribute("member") RegisterMemberForm registerMemberForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.info("검증 오류 발생 errors = {}", bindingResult);
            return "basic/registerMemberForm";
        }

        MemberDto memberDto = MemberDto.builder()
                .email(registerMemberForm.getEmail())
                .password(registerMemberForm.getPassword())
                .name(registerMemberForm.getName())
                .phoneNumber(registerMemberForm.getPhoneNumber()).build();

        memberService.registerMember(memberDto);

        log.info("회원가입 성공!");
        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("form") LoginForm form){
    return "basic/loginForm";
    }


    @PostMapping("/login")
    public String login2(@Validated @ModelAttribute("form") LoginForm form,BindingResult bindingResult ,
                         @RequestParam(defaultValue = "/") String redirectURL,
                         HttpServletRequest request){

        log.info("email:{}",form.getEmail());
        log.info("password:{}",form.getPassword());

        if (bindingResult.hasErrors()) {
            log.info("검증 오류 발생 errors = {}", bindingResult);
            return "basic/loginForm";
        }

        Member loginMember = memberService.login(form.getEmail(),form.getPassword());

        if(loginMember == null){
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "basic/loginForm";
        }

        HttpSession session = request.getSession();
        session.setAttribute(SessionID.LOGIN_MEMBER, loginMember.getId());

        log.info("redirect :{} 로 이동",redirectURL);
        return "redirect:"+redirectURL;
    }

//    @PostMapping("/logout")
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false); /**가져올때 없어도 만들지 않는다*/
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

    @GetMapping("/info")
    public String memberInfo(Model model,HttpSession request) {
//        SessionDto session = (SessionDto) request.getAttribute(SessionID.LOGIN_MEMBER);
        Long sessionId = (Long) request.getAttribute(SessionID.LOGIN_MEMBER);
        MemberDto findMember = memberService.findOneById(sessionId);
        log.info("session : {}",sessionId);

        model.addAttribute("member",findMember);
        if(findMember.getMemberType().equals(MemberType.ADMIN))
            return "basic/admin/adminHome";


        return "basic/members/myPage";
    }

    @GetMapping("/update")
    public String updateForm(Model model , HttpSession request){

        Long sessionId = (Long) request.getAttribute(SessionID.LOGIN_MEMBER);
        MemberDto memberDto = memberService.findOneById(sessionId);
        MemberUpdateForm form = MemberUpdateForm.makeForm(memberDto);
        model.addAttribute("form",form);
        return "basic/members/memberUpdateProfile";
    }
    @PostMapping("/update")
    public String updateMember(MemberUpdateForm updateForm){

        MemberDto updateDto = MemberDto.builder()
                        .id(updateForm.getId())
                .email(updateForm.getEmail())
                .password(updateForm.getPassword())
                .build();
        
        memberService.updateMember(updateDto);
        return "redirect:/members/info";
    }


}
