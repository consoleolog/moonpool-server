package org.leo.moonpool.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.leo.moonpool.dto.AnswerDto;
import org.leo.moonpool.dto.SalesDto;
import org.leo.moonpool.dto.SalesListDto;
import org.leo.moonpool.service.impl.SalesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RequiredArgsConstructor
@RestController
public class SalesController {
    private SalesService salesService;

    @PostMapping("/mp/sales/purcahse")
    public ResponseEntity<?> purchase(@RequestBody SalesDto salesDto){
        String result = salesService.purchase(salesDto);
        return ResponseEntity.ok(result);
    }
    @PostMapping("/mp/sales/purchase/all")
    public ResponseEntity<?> purchaseAll(@RequestBody SalesListDto salesListDto){
        String result = salesService.purchaseAll(salesListDto);
        return ResponseEntity.ok(result);
    }
    @PostMapping("/mp/problems/answer/check")
    public ResponseEntity<?> answerCheck(@RequestBody AnswerDto answerDto){
        String result = salesService.answerCheck(answerDto);
        return ResponseEntity.ok(result);
    }
}
