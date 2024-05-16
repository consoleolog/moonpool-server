package org.leo.moonpool.service.impl;

import jakarta.transaction.Transactional;
import org.leo.moonpool.dto.CartDto;

import java.util.List;

@Transactional
public interface CartService {

    String register(CartDto cartDto);
    List<?> getList(Long memberId);

    String delete(Long cartId, Long memberId);

    String deleteAll(Long memberId);

    String alreadyRegister(CartDto cartDto);
}
