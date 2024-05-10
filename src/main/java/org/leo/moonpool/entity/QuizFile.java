package org.leo.moonpool.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Embeddable
public class QuizFile {
    private String quizName;
}
