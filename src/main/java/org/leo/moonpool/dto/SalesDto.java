package org.leo.moonpool.dto;

import lombok.*;
import org.leo.moonpool.entity.Sales;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class SalesDto {

    private Long id;

    private Long problemId;

    private Long memberId;

    public Sales toEntity(SalesDto salesDto){
        return Sales.builder()
                .problemId(salesDto.getProblemId())
                .memberId(salesDto.getMemberId())
                .build();
    }
}
