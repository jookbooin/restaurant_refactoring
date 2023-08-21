package com.restaurant.reservation.service;

import com.restaurant.reservation.domain.dto.MemberDto;
import com.restaurant.reservation.domain.members.Member;
import com.restaurant.reservation.repository.MemberRepository;
import com.restaurant.reservation.repository.dto.MemberSearch;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

    public MemberDto findOneById(Long id){
//        Member findMember = memberRepository.findById(id).orElseThrow(() -> new RuntimeException("can't find Member"));

        if (id != null) {
            Optional<Member> memberOps = memberRepository.findById(id);
            if(memberOps.isPresent()) {
                Member findMember = memberOps.get();
                MemberDto memberDto = MemberDto.builder()
                        .id(findMember.getId())
                        .email(findMember.getEmail())
                        .name(findMember.getMemberInfo().getName())
                        .phoneNumber(findMember.getMemberInfo().getPhoneNumber())
                        .password(findMember.getPassword())
                        .memberRole(findMember.getMemberRole())
                        .build();
                return memberDto;
            }
        }

        return null;
    }
    @Transactional
    public void updateMember(MemberDto memberDto){

        Member findMember = memberRepository.findById(memberDto.getId())
                .orElseThrow(() -> new RuntimeException("can't find Member"));
        log.info("find!");

        findMember.update(memberDto.getEmail(), memberDto.getPassword());
        log.info("update!");
    }
    public Page<Member> findMemberAll(MemberSearch condition , Pageable pageable){

        Page<Member> memberPage = memberRepository.findMemberSearchCondition(condition, pageable);
        return memberPage;
    }

    public Member login(String email , String password){
        return memberRepository.findByEmail(email)
                .filter(m->m.getPassword().equals(password))
                .orElse(null);
    }




}
