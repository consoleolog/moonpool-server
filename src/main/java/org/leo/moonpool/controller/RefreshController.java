package org.leo.moonpool.controller;

import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.leo.moonpool.config.JwtConfig;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
@RestController
public class RefreshController {
    @GetMapping("/mp/refresh")
    public Map<String, Object> refresh(@RequestHeader("Authorization") String authHeader,
                                       String refreshToken){
        log.info(authHeader);
        log.info(refreshToken);
        if (refreshToken==null){
            throw new JwtException("Refresh_Token is Null");
        }
        if (authHeader == null || authHeader.length() < 7){
            throw new JwtException("Invalid String");
        }
        String accessToken = authHeader.substring(7);
        log.info("accesstoken"+accessToken);
        // AccessToken 만료 여부 확인
        if (!checkExpiredToken(accessToken)){
            return Map.of("AccessToken",accessToken,"RefreshToken",refreshToken);
        }
        // Refresh 토큰 검증 checkexpired 가 true 면 실행할 코드
        Map<String, Object> claims = JwtConfig.validateToken(refreshToken);
        String newAccessToken = JwtConfig.generateToken(claims,10);
        String newRefreshToken = JwtConfig.generateToken(claims, 60 * 24);
        return Map.of("AccessToken",newAccessToken,"RefreshToken",newRefreshToken);
    }
    private Boolean checkTime(Integer exp){
        // jwt exp 를 날짜로 변환
        Date expDate = new Date((long) exp * (1000));
        long gap = expDate.getTime() - System.currentTimeMillis();
        long leftMin = gap / (1000 * 60);
        // 60분이 남았는지 안남았는지
        return leftMin < 60;
    }
    // expried 라는 메시지면 새로 발급해줄게
    private Boolean checkExpiredToken(String token){
        try {
            JwtConfig.validateToken(token);
        } catch (JwtException e){
            if (e.getMessage().equals("Expired")){
                return true;
            }
        }
        return false;
    }
}
