package org.leo.moonpool.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Entity
public class Answer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerId;

    private Long answer;

    private Long memberId;
}
