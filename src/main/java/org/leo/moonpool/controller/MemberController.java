package org.leo.moonpool.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.leo.moonpool.dto.ApiUser;
import org.leo.moonpool.dto.MemberDataDto;
import org.leo.moonpool.dto.MemberDto;
import org.leo.moonpool.handler.MemberHandler;
import org.leo.moonpool.repository.MemberRepository;
import org.leo.moonpool.service.impl.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Log4j2
@RequiredArgsConstructor
@RestController
public class MemberController {
    private final MemberService memberService;
    private final MemberHandler memberHandler;
    @PostMapping("/mp/members/register")
    public ResponseEntity<?> register(@RequestBody MemberDto memberDto){
        memberService.register(memberDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("/");
    }
    // 요청한 유저의 아이디랑 가지고 싶어하는 회원의 아이디가 같으면 보내주는거야

    @GetMapping("/mp/members/get-user-data")
    public ResponseEntity<?> getUserData(@RequestParam("username")String username,@RequestParam("memberId")Long memberId){
        Boolean validCheck =  memberHandler.validCheck(memberId);
        if (!validCheck){
            return ResponseEntity.ok("Invalid_Error");
        }
        MemberDataDto result = memberService.getUserData(username,memberId);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/mp/members/login-check")
    public ResponseEntity<?> loginCheck(){
        var result =  SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return ResponseEntity.ok(result);
    }
    @GetMapping("/mp/user-info")
    public ResponseEntity<?> userData(){
        var result = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(result);
    }

}
