package org.leo.moonpool.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.leo.moonpool.dto.MemberDataDto;
import org.leo.moonpool.dto.MemberDto;
import org.leo.moonpool.service.impl.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Log4j2
@RequiredArgsConstructor
@RestController
public class MemberController {
    private final MemberService memberService;
    @PostMapping("/mp/members/register")
    public ResponseEntity<?> register(@RequestBody MemberDto memberDto){
        memberService.register(memberDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("/");
    }
    // 요청한 유저의 아이디랑 가지고 싶어하는 회원의 아이디가 같으면 보내주는거야
    @GetMapping("/mp/login/members-data")
    public ResponseEntity<MemberDataDto> getUserData(@RequestParam("memberId")Long memberId){
        MemberDataDto result = memberService.getUserData(memberId);
        return ResponseEntity.ok(result);
    }
    @PostMapping("/mp/login/members/update")
    public ResponseEntity<String> updateUserData(@RequestBody MemberDataDto memberDataDto){
        String result = memberService.updateMemberData(memberDataDto);
        return ResponseEntity.ok(result);
    }
}
