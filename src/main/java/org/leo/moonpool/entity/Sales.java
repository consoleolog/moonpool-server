package org.leo.moonpool.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Entity
public class Sales {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long salesId;

    private Long problemId;

    private Long memberId;


}
