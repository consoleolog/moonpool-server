package org.leo.moonpool.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
public class ApiUser extends User {
    private Long memberId;
    private String username;
    private String password;
    private String displayName;
    private String intro;
    private String educationState;
    private Integer coin;
    private Collection authorities;
    public ApiUser( Long memberId, String username, String password,String displayName,String intro,String educationState,Integer coin, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.memberId = memberId;
        this.username = username;
        this.password = password;
        this.intro = intro;
        this.displayName = displayName;
        this.educationState = educationState;
        this.coin = coin;
        this.authorities = authorities;
    }
    public Map<String, Object> getClaims(){
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("memberId",memberId);
        dataMap.put("username", username);
        dataMap.put("password",password);
        dataMap.put("intro",intro);
        dataMap.put("displayName",displayName);
        dataMap.put("educationState",educationState);
        dataMap.put("coin",coin);
        dataMap.put("roleNames",authorities);
        return dataMap;
    }
}
