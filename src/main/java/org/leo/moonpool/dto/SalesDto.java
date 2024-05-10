package org.leo.moonpool.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class SalesDto {

    private Long id;

    private Long problemId;

    private Long memberId;
}
