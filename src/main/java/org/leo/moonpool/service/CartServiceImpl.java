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
import java.util.Objects;

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

    @Override
    public String alreadyRegister(CartDto cartDto) {
        // 문제 아이디를 받아와서 이게 회원의 장바구니에 있는지 없는지 확인해주는거임
        // 회원 검증
        Boolean validCheck = memberHandler.validCheck(cartDto.getOwnerId());
        if ( !validCheck){
            return "VALID_ERROR";
        }
        // 장바구니에 있는지 없는지 확인
        List<Long> cartItemList = cartRepository.customFindByMemberId(cartDto.getOwnerId());
        for ( Long problemId:cartItemList ){
            if(Objects.equals(problemId, cartDto.getProblemId())){
                return "ALREADY_EXIST";
            }
        }
        return "SUCCESS";
    }


}
