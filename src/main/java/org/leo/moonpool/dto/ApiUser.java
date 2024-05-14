package org.leo.moonpool.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
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

    public ApiUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
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
        return dataMap;
    }
}
