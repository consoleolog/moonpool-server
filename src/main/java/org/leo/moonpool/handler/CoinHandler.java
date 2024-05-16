package org.leo.moonpool.handler;

import lombok.RequiredArgsConstructor;
import org.leo.moonpool.entity.Member;
import org.leo.moonpool.repository.MemberRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class CoinHandler {
    private final MemberRepository memberRepository;
    public void plus(Long memberId, Integer coin){
        Optional<Member> result = memberRepository.findById(memberId);
        if(result.isPresent()){
            Member member = result.get();
            member.changeCoin(result.get().getCoin() + coin);
            memberRepository.save(member);
        }
    }

    public String minus(Long memberId, Integer coin){
        Optional<Member> result = memberRepository.findById(memberId);
        if(result.isPresent()){
            Member member = result.get();
            if ( member.getCoin() - coin < 0){
                return "COIN_LACK";
            } else {
                member.changeCoin(result.get().getCoin() - coin);
                memberRepository.save(member);
            }
        }
        return "SUCCESS";
    }


}
