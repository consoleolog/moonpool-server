package org.leo.moonpool.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Embeddable
public class AnswerImage {
    private String answerName;
}
