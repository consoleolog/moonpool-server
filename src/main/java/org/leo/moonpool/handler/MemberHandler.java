package org.leo.moonpool.handler;

import lombok.extern.log4j.Log4j2;
import org.leo.moonpool.dto.ApiUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Log4j2
@Component
public class MemberHandler {
    public Boolean validCheck(Long memberId){
        ApiUser loginUser = (ApiUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long loginUserId = loginUser.getMemberId();
        if (!Objects.equals(memberId, loginUserId)){
            return false;
        }
        return true;
    }

}
