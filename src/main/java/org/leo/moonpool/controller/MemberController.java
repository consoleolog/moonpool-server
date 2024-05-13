package org.leo.moonpool.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.leo.moonpool.dto.MemberDataDto;
import org.leo.moonpool.dto.MemberDto;
import org.leo.moonpool.service.impl.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RequiredArgsConstructor
@RequestMapping("/mp/members")
@RestController
public class MemberController {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody MemberDto memberDto){
        memberService.register(memberDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("/");
    }
    @GetMapping("/get-user-data")
    public ResponseEntity<?> getUserData(@RequestParam("username")String username,@RequestParam("memberId")Long memberId){
        MemberDataDto result = memberService.getUserData(username,memberId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/login-check")
    public ResponseEntity<?> loginCheck(){
        var result = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(result);
    }
}
