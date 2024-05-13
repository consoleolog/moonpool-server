package org.leo.moonpool.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class MemberDto {
    private String username;

    private String password;

    private String passwordCheck;

    private String displayName;

    private String intro;

    private String educationState;


}
