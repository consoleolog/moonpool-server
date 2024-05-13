package org.leo.moonpool.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Embeddable
public class QuizImage {
    private String quizName;
}
