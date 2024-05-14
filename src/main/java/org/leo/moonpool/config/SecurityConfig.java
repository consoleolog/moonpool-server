package org.leo.moonpool.config;

import lombok.RequiredArgsConstructor;
import org.leo.moonpool.handler.CustomAccessDeniedHandler;
import org.leo.moonpool.handler.LoginFailHandler;
import org.leo.moonpool.handler.LoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@EnableMethodSecurity
@Configuration
public class SecurityConfig {


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(cors -> { //cors설정을 사용하겠다 라는 코드
            cors.configurationSource(corsConfigurationSource());
        });
        http.csrf(csrf->{
            csrf.disable();
        });
        http.sessionManagement(session -> {//세션 생성 안함
            session.sessionCreationPolicy(SessionCreationPolicy.NEVER);
        });
        http.authorizeHttpRequests(config -> {
            config.requestMatchers("/**").permitAll();
        });
        http.formLogin(formLogin -> {
            formLogin.loginPage("/mp/members/login");
            formLogin.successHandler(new LoginSuccessHandler());
            formLogin.failureHandler(new LoginFailHandler());
        });
        // UsernamePasswordAuthenticationFilter.class 전에 jwtcheckfilter 실행 시켜줘
        http.addFilterBefore(new JwtCheckFilter(), UsernamePasswordAuthenticationFilter.class);
        //멤버 권한 예외 전역 처리
        http.exceptionHandling(config -> {
            config.accessDeniedHandler(new CustomAccessDeniedHandler());
        });
        return http.build();
    }

    @Bean //cors 설정
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        //http://localhost:3000","https://consoleolog.github.io/
        configuration.setAllowedOrigins(List.of("http://localhost:3000/", "https://consoleolog.github.io/"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
