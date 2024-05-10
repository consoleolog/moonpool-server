package org.leo.moonpool.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Embeddable
public class AnswerFile {
    private String answerName;
}
