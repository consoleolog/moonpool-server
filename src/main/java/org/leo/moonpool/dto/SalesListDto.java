package org.leo.moonpool.dto;

import lombok.*;

import java.util.List;
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SalesListDto {
    private List<Long> problemIdList;
    private Long memberId;

}
