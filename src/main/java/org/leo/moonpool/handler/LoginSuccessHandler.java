package org.leo.moonpool.handler;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.leo.moonpool.config.JwtConfig;
import org.leo.moonpool.dto.ApiUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@Service
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        ApiUser apiUser = (ApiUser) authentication.getPrincipal();
        Map<String,Object> claims = apiUser.getClaims();
        // 일반적으로 엑세스 토큰만 사용
        String accessToken = JwtConfig.generateToken(claims, 5); //10분
        // 엑세스 토큰이 만료되면 엑세스랑 리프레쉬를 같이 보내서 새로운 엑세스 토큰 발급
        String refreshToken = JwtConfig.generateToken(claims, 60*24);
//        claims.put("userInfo",userDto);
        claims.put("accessToken", accessToken);
        claims.put("refreshToken",refreshToken);
        Gson gson = new Gson();
        String jsonStr = gson.toJson(claims);
        response.setContentType("application/json; charset=UTF-8");
        PrintWriter printWriter = response.getWriter();
        printWriter.println(jsonStr);
        printWriter.close();
    }
}

