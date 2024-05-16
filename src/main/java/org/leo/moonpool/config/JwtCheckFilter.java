package org.leo.moonpool.config;

import com.google.gson.Gson;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.leo.moonpool.dto.ApiUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Log4j2
public class JwtCheckFilter extends OncePerRequestFilter {
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws SecurityException {
        String path = request.getRequestURI();
        if (path.startsWith("/mp/members/login")|| path.startsWith("/mp/problems")||path.startsWith("/mp/comments")){
            return true;
        }
        return false;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 필터 체크하는 함수
        String authHeader = request.getHeader("Authorization");
        log.info("-----------------check filter start--------------------");
        try {
            // Bearer 타입( 공식 문서에 이렇게 쓰라네)
            String accessToken = authHeader.substring(7);
            log.info(accessToken);
            Map<String, Object> claims = JwtConfig.validateToken(accessToken);
            var result = claims.get("memberId").toString();
            Long memberId = Long.valueOf(result);

            String username = claims.get("username").toString();

            String password = claims.get("password").toString();

            String displayName = claims.get("displayName").toString();

            String intro = claims.get("intro").toString();

            String educationState = claims.get("educationState").toString();

            Integer coin = (Integer) claims.get("coin");

            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            log.info(authorities);
            ApiUser apiUser = new ApiUser(username,password, authorities);
            apiUser.setMemberId(memberId);
            apiUser.setUsername(username);
            apiUser.setIntro(intro);
            apiUser.setDisplayName(displayName);
            apiUser.setEducationState(educationState);
            apiUser.setCoin(coin);
            log.info(apiUser);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(apiUser,password,apiUser.getAuthorities());
            log.info(authenticationToken);
            // 인증 완료하면 contextholder 에 userdto 를 저장
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authenticationToken);
            SecurityContextHolder.setContext(context);
            log.info(context);
            filterChain.doFilter(request, response);
            log.info("-----------------check filter end--------------------");
        } catch (Exception e){
            log.info("------------------accesstoken error start---------------------------------");
            Gson gson = new Gson();
            String message = gson.toJson(Map.of("Error","Error_Access_Token"));
            response.setContentType("application/json");
            PrintWriter printWriter = response.getWriter();
            printWriter.println(message);
            printWriter.close();
            log.info("------------------accesstoken error end---------------------------------");
        }
    }
}
