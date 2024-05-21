package org.leo.moonpool.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.leo.moonpool.dto.AnswerDto;
import org.leo.moonpool.dto.SalesDto;
import org.leo.moonpool.dto.SalesListDto;
import org.leo.moonpool.service.impl.SalesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Log4j2
@RequiredArgsConstructor
@RestController
public class SalesController {
    private final SalesService salesService;
    @PostMapping("/mp/sales/purchase")
    public ResponseEntity<?> purchase(@RequestBody SalesDto salesDto){
        String result = salesService.purchase(salesDto);
        return ResponseEntity.ok(result);
    }
    @PostMapping("/mp/sales/purchased-check")
    public ResponseEntity<String> purchasedCheckAll(@RequestBody SalesListDto salesListDto){
        log.info(salesListDto);
        String result = salesService.purchaseCheckAll(salesListDto);
        return ResponseEntity.ok(result);
    }
    @PostMapping("/mp/sales/purchase-all")
    public ResponseEntity<?> purchaseAll(@RequestBody SalesListDto salesListDto){
        String result = salesService.purchaseAll(salesListDto);
//        var result = salesListDto;
//        log.info(salesListDto.getProblemIdList());
        return ResponseEntity.ok(result);
    }
    @PostMapping("/mp/sales/answer/check")
    public ResponseEntity<?> answerCheck(@RequestBody AnswerDto answerDto){
        String result = salesService.answerCheck(answerDto);
        return ResponseEntity.ok(result);
    }
    @PostMapping("/mp/sales/purchase-check")
    public ResponseEntity<String> purchaseCheck(@RequestBody SalesDto salesDto){
        log.info(salesDto);
        String result = salesService.alreadyPurchase(salesDto);
        log.info(result);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/mp/sales/purchased-list/{pageNum}")
    public ResponseEntity<Map<String, Object>> getPurchasedList(@PathVariable("pageNum")Integer pageNum,
                                                @RequestParam("memberId")Long memberId){
        Map<String, Object> result = salesService.getPurchasedItem(memberId,pageNum);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/mp/sales/made-list/{pageNum}")
    public ResponseEntity<Map<String, Object>> getMadeList(@PathVariable("pageNum")Integer pageNum,
                                           @RequestParam("memberId")Long memberId){
        Map<String, Object> result = salesService.getMadeList(memberId,pageNum);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/mp/sales/get-sales-list/{memberId}")
    public ResponseEntity<?> getSalesList(@PathVariable("memberId")Long memberId){
        var result = salesService.getSalesList(memberId);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/mp/sales/check-sales-one/{memberId}")
    public ResponseEntity<?> checkSalesOne(@PathVariable("memberId")Long memberId,
                                           @RequestParam("problemId")Long problemId){
        var result = salesService.checkSalesOne(problemId,memberId);
        return ResponseEntity.ok(result);
    }
}
