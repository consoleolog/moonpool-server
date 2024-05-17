package org.leo.moonpool.service;

import lombok.RequiredArgsConstructor;
import org.leo.moonpool.dto.ApiUser;
import org.leo.moonpool.dto.MemberDataDto;
import org.leo.moonpool.dto.MemberDto;
import org.leo.moonpool.entity.Member;
import org.leo.moonpool.handler.MemberHandler;
import org.leo.moonpool.repository.MemberRepository;
import org.leo.moonpool.service.impl.MemberService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final MemberHandler memberHandler;

    @Override
    public void register(MemberDto memberDto) {
        Member member = Member.builder()
                .username(memberDto.getUsername())
                .password(passwordEncoder.encode(memberDto.getPassword()))
                .displayName(memberDto.getDisplayName())
                .intro(memberDto.getIntro())
                .educationState(memberDto.getEducationState())
                .build();
        memberRepository.save(member);
    }
    @Override
    public String updateMemberData(MemberDataDto memberDataDto){
        Boolean validCheck = memberHandler.validCheck(memberDataDto.getMemberId());
        if(!validCheck){
            return "VALID_ERROR";
        }
        Optional<Member> result = memberRepository.findById(memberDataDto.getMemberId());
        Member member = result.orElseThrow();
        member.changeDisplayName(memberDataDto.getDisplayName());
        member.changeIntro(memberDataDto.getIntro());
        member.changeEducationState(memberDataDto.getEducationState());
        memberRepository.save(member);
        return "SUCCESS";
    }
    @Override
    public MemberDataDto getUserData(Long memberId){
        Boolean validCheck = memberHandler.validCheck(memberId);
        if(!validCheck){
            return null;
        }
        ApiUser apiUser = (ApiUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MemberDataDto user = MemberDataDto.builder()
                .memberId(apiUser.getMemberId())
                .username(apiUser.getUsername())
                .displayName(apiUser.getDisplayName())
                .intro(apiUser.getIntro())
                .educationState(apiUser.getEducationState())
                .coin(apiUser.getCoin())
                .build();
        return user;
    }
}
