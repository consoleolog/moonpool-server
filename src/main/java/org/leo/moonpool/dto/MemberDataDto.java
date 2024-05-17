package org.leo.moonpool.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MemberDataDto {
    private Long memberId;
    private String username;
    private String displayName;
    private String intro ;
    private String educationState;
    private Integer coin;

}
