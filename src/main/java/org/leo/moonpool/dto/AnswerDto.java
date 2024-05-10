package org.leo.moonpool.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AnswerDto {
    private Long answerId;

    private Integer answer;

    private Long problemId;

    private Long memberId;
}
