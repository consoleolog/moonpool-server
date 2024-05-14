package org.leo.moonpool.service.impl;

import jakarta.transaction.Transactional;
import org.leo.moonpool.dto.CartDto;

import java.util.List;

@Transactional
public interface CartService {

    String register(CartDto cartDto);
    // 내가 자바스크립트 잘해서 맘대로 할 수도 있잖아 뭘 막아야하지? 요청한 유저의 id가 진짜 얘 아이디인지 아닌지 확인을 해봐야될거 아니야 그럼 서버에서는 뭘로 조회흫 해야해 이미 서버에 있는걸로 꺼내야지
    List<?> getList(Long memberId);

    String delete(Long cartId, Long memberId);

    String deleteAll(Long memberId);
}
