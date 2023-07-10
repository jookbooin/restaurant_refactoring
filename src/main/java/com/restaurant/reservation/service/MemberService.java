package com.restaurant.reservation.service;

import com.restaurant.reservation.repository.MemberRepository;
import com.restaurant.reservation.domain.members.Member;
import com.restaurant.reservation.domain.Dto.MemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public Member registerMember(MemberDto memberDto){
        log.info("MemberService - registerMember");
        log.info("memberDto:{}",memberDto);
//         중복 검증
        Member member = Member.createCustomer(memberDto);

        return memberRepository.save(member);
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOneById(Long id){
        return memberRepository.findById(id).orElseThrow(() -> new RuntimeException("can't find Member"));
    }

    public Member login(String email , String password){
        return memberRepository.findByEmail(email)
                .filter(m->m.getPassword().equals(password))
                .orElse(null);
    }


}
