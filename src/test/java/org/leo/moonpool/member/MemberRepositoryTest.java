package org.leo.moonpool.member;

import org.junit.jupiter.api.Test;
import org.leo.moonpool.entity.Member;
import org.leo.moonpool.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testRegister(){
        for (int i = 0; i < 10; i++) {
            Member member = Member.builder()
                    .username("test"+i+"@test.com")
                    .password(passwordEncoder.encode("1111"))
                    .displayName("test"+i)
                    .intro("test intro"+i+"........")
                    .educationState("university")
                    .build();
            memberRepository.save(member);
        }
    }
}
