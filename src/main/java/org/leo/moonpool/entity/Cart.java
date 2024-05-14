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
public class Cart {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    private Long problemId;

    private Long ownerId;
}
