package com.restaurant.reservation.Controller;

import com.restaurant.reservation.web.form.LoginForm;
import com.restaurant.reservation.web.form.RegisterMemberForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {


    @GetMapping("/register")
    public String addForm(@ModelAttribute("member") RegisterMemberForm registerMemberForm) {
        return "members/registerMemberForm";
    }

    @PostMapping("register")
    public String save(@Valid @ModelAttribute("member") RegisterMemberForm registerMemberForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "members/registerMemberForm";
        }

        return "redirect:/";
    }

    @GetMapping("login")
    public String loginForm(@ModelAttribute("form") LoginForm form){

    return "members/loginForm";
    }
}
