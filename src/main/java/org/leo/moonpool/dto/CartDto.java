package org.leo.moonpool.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.leo.moonpool.entity.Cart;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartDto {
    private Long cartId;

    private Long problemId;

    private Long ownerId;

    public Cart toEntity(CartDto cartDto){
        Cart cart = Cart.builder()
                .problemId(cartDto.getProblemId())
                .ownerId(cartDto.getOwnerId())
                .build();
        return cart;
    }

}
