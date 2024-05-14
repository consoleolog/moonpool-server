package org.leo.moonpool.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.leo.moonpool.dto.CartDto;
import org.leo.moonpool.service.impl.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@RestController
public class CartController {
    private final CartService cartService;
    @PostMapping("/mp/carts/register")
    public ResponseEntity<String> register(@RequestBody CartDto cartDto){
        String result = cartService.register(cartDto);
        return ResponseEntity.status(200).body(result);
    }
    @GetMapping("/mp/carts")
    public ResponseEntity<?> getList(@RequestParam("memberId") Long memberId){
        List result = cartService.getList(memberId);
        return ResponseEntity.ok(result);
    }
    @DeleteMapping("/mp/carts/delete/{cartId}")
    public ResponseEntity<String> deleteCartItem(@RequestParam("memberId")Long memberId,
                                                 @PathVariable("cartId")Long cartId){
        String result = cartService.delete(cartId,memberId);
        return ResponseEntity.ok(result);
    }
    @DeleteMapping("/mp/carts/delete/all")
    public ResponseEntity<String> deleteAll(@RequestParam("memberId")Long memberId){
        String result = cartService.deleteAll(memberId);
        return ResponseEntity.ok(result);
    }
}
