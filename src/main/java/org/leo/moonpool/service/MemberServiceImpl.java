package org.leo.moonpool.service;

import lombok.RequiredArgsConstructor;
import org.leo.moonpool.dto.MemberDataDto;
import org.leo.moonpool.dto.MemberDto;
import org.leo.moonpool.entity.Member;
import org.leo.moonpool.repository.MemberRepository;
import org.leo.moonpool.service.impl.MemberService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public MemberDataDto getUserData(String username , Long memberId) {
        Member member = memberRepository.customFindByMemberIdAndUsername(memberId,username);
        MemberDataDto memberDataDto = MemberDataDto.builder()
                .memberId(member.getMemberId())
                .username(member.getUsername())
                .displayName(member.getDisplayName())
                .intro(member.getIntro())
                .educationState(member.getEducationState())
                .coin(member.getCoin())
                .build();
        return memberDataDto;
    }

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
}
