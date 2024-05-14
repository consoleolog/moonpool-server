package org.leo.moonpool.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.leo.moonpool.dto.ApiUser;
import org.leo.moonpool.entity.Member;
import org.leo.moonpool.repository.MemberRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Log4j2
@RequiredArgsConstructor
@Service
public class UsersDetailService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> result = memberRepository.findByUsername(username);
        if(result.isEmpty()){
            throw new UsernameNotFoundException("NOT_FOUND");
        }
        Member user = result.get();
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        ApiUser apiUser = new ApiUser(user.getUsername(),user.getPassword(),authorities);
        apiUser.setMemberId(user.getMemberId());
        apiUser.setUsername(user.getUsername());
        apiUser.setPassword(user.getPassword());
        apiUser.setDisplayName(user.getDisplayName());
        apiUser.setIntro(user.getIntro());
        apiUser.setEducationState(user.getEducationState());
        apiUser.setCoin(user.getCoin());
        log.info(apiUser);
        return apiUser;
    }
}
