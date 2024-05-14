package org.leo.moonpool.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.leo.moonpool.dto.CartDto;
import org.leo.moonpool.handler.MemberHandler;
import org.leo.moonpool.repository.CartRepository;
import org.leo.moonpool.service.impl.CartService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Log4j2
@RequiredArgsConstructor
@Service
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final MemberHandler memberHandler;
    @Override
    public String register(CartDto cartDto) {
        Boolean validCheck = memberHandler.validCheck(cartDto.getOwnerId());
        log.info(validCheck);
        if (validCheck){
            cartRepository.save(cartDto.toEntity(cartDto));
            return "SUCCESS";
        } else {
            return "ERROR";
        }
    }

    @Override
    public List<?> getList(Long memberId) {
        Boolean validCheck = memberHandler.validCheck(memberId);
        if (validCheck){
            return cartRepository.customFindAll(memberId);
        }
        return new ArrayList<>();
    }

    @Override
    public String delete(Long cartId, Long memberId) {
        Boolean validCheck = memberHandler.validCheck(memberId);
        if (validCheck){
            cartRepository.deleteById(cartId);
            return "SUCCESS";
        }
        return "ERROR";
    }

    @Override
    public String deleteAll(Long memberId) {
        Boolean validCheck = memberHandler.validCheck(memberId);
        if(validCheck){
            cartRepository.customDeleteAll(memberId);
            return "SUCCESS";
        }
        return "ERROR";
    }

}
