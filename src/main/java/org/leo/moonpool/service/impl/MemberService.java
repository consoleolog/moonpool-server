package org.leo.moonpool.service.impl;

import jakarta.transaction.Transactional;
import org.leo.moonpool.dto.MemberDataDto;
import org.leo.moonpool.dto.MemberDto;

@Transactional
public interface MemberService {

    MemberDataDto getUserData(String username, Long memberId);

    void register(MemberDto memberDto);

}
