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

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Problem problem;

    private Long memberId;


}
