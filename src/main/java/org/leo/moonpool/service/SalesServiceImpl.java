package org.leo.moonpool.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.leo.moonpool.dto.AnswerDto;
import org.leo.moonpool.dto.SalesDto;
import org.leo.moonpool.dto.SalesListDto;
import org.leo.moonpool.entity.Member;
import org.leo.moonpool.entity.Problem;
import org.leo.moonpool.handler.CoinHandler;
import org.leo.moonpool.handler.MemberHandler;
import org.leo.moonpool.repository.MemberRepository;
import org.leo.moonpool.repository.ProblemRepository;
import org.leo.moonpool.repository.SalesRepository;
import org.leo.moonpool.service.impl.SalesService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.IntStream;
@Log4j2
@RequiredArgsConstructor
@Service
public class SalesServiceImpl implements SalesService {
    private final ProblemRepository problemRepository;
    private final SalesRepository salesRepository;
    private final CoinHandler coinHandler;
    private final MemberHandler memberHandler;
    private final MemberRepository memberRepository;
    @Override
    public String purchase(SalesDto salesDto) {
        Optional<Problem> result = problemRepository.findById(salesDto.getProblemId());
        Problem problem = result.orElseThrow();
        Boolean validCheck = memberHandler.validCheck(salesDto.getMemberId());
        if (!validCheck){
            return "VALID_ERROR";
        }
        salesRepository.save(salesDto.toEntity(salesDto));
        try{
            String coinMsg = coinHandler.minus(salesDto.getMemberId(),problem.getPrice());
            coinHandler.plus(problem.getWriterId(),problem.getPrice());
            return coinMsg;
        } catch (Exception e){
            return e.getMessage();
        }
    }

    @Override
    public String purchaseAll(SalesListDto salesListDto) {
        SalesDto salesDto = new SalesDto();
        String coinMsg = "";
        for (Long problemId : salesListDto.getProblemIdList()) {
            Optional<Problem> result = problemRepository.findById(problemId);
            Problem problem = result.orElseThrow();
            Long problemWriterId = salesRepository.findMemberIdByProblemId(problemId);
            try {
                coinMsg = coinHandler.minus(salesListDto.getMemberId(), salesListDto.getTotalPrice());
                if (Objects.equals(coinMsg, "SUCCESS")){
                    salesDto.setProblemId(problemId);
                    log.info(coinMsg);
                    salesDto.setMemberId(salesListDto.getMemberId());
                    coinHandler.plus(problemWriterId,problem.getPrice());
                    salesRepository.save(salesDto.toEntity(salesDto));
                }

        }
        catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return coinMsg;
    }

    @Override
    public String answerCheck(AnswerDto answerDto) {
        Optional<Problem> result = problemRepository.findById(answerDto.getProblemId());
        Problem problem = result.orElseThrow();
        log.info(problem);
        log.info(answerDto.getAnswer());
        String message = "";
        Boolean validCheck = memberHandler.validCheck(answerDto.getMemberId());
        if (!validCheck){
            return "VALID_ERROR";
        }
        if(Objects.equals(problem.getAnswer(), answerDto.getAnswer())){
            try {
                coinHandler.plus(answerDto.getMemberId(),100);
                message = "CORRECT";
            }catch (Exception e){
                return "ERROR";
            }
        } else {
            message = "INCORRECT";
        }
        return message;
    }
    @Override
    public String purchaseCheckAll(SalesListDto salesListDto){
        Boolean validCheck = memberHandler.validCheck(salesListDto.getMemberId());
        String result = "SUCCESS";
        if (!validCheck){
            return "VALID_ERROR";
        }
        List<Long> problemIdList = salesListDto.getProblemIdList();
        List<Long> purcahsedItemList = salesRepository.customFindByMemberId(salesListDto.getMemberId());
        System.out.println(purcahsedItemList);
        // 두개 중에 겹치는게 있나봐야됨
        for (Long i : problemIdList) {
            for (Long j : purcahsedItemList) {
                System.out.println(i);
                System.out.println(j);
                System.out.println("---------------");
                if (Objects.equals(i, j)){
                    result = "ALREADY_PURCHASED";
                }
            }
        }
        return result;
    }
    @Override
    public String alreadyPurchase(SalesDto salesDto) {
        Boolean validCheck = memberHandler.validCheck(salesDto.getMemberId());
        if (!validCheck){
            return "VALID_ERROR";
        }
        List<Long> purchasedItemList = salesRepository.customFindByMemberId(salesDto.getMemberId());
        for (Long problemId:purchasedItemList){
            if (Objects.equals(problemId, salesDto.getProblemId())){
                return "ALREADY_PURCHASED";
            }
        }
        return "SUCCESS";
    }

    @Override
    public Map<String, Object> getPurchasedItem(Long memberId,Integer pageNum ) {
        Boolean validCheck = memberHandler.validCheck(memberId);
        if (!validCheck){
            return Map.of("Message","VALID_ERROR");
        }
        List<?> problemList = salesRepository.customFindAll(memberId,(pageNum -1) * 10);
        Long totalCount = salesRepository.countByMemberId(memberId);
        int end = (int)(Math.ceil(pageNum /10.0)) * 10; //10
        int start = end - 9;
        int last = (int)(Math.ceil((double) totalCount/(double) 10.0)); // 11
        end = end < last ? end : last; // 10 < 11 ?
        start = start < 1 ? 1 : start;
        List<Integer> numList = IntStream.rangeClosed(start, end).boxed().toList();
        boolean prevPage = start > 1;
        boolean nextPage = totalCount > end* 10L;
        int prev = prevPage ? start-1 : 0;
        int next = nextPage ? end + 1 : 0; // end + 1
        Map<String, Object> result = new HashMap<>();
        result.put("problemList", problemList);
        result.put("end", end);
        result.put("start",start);
        result.put("prev",prev);
        result.put("next",next);
        result.put("numList",numList);
        return result;
    }

    @Override
    public Map<String, Object> getMadeList(Long memberId, Integer pageNum) {
        Boolean validCheck = memberHandler.validCheck(memberId);
        if (!validCheck){
            return Map.of("Message","VALID_ERROR");
        }
        List<?> problemList = salesRepository.customFindMadeList(memberId,(pageNum -1) * 10);
        Long totalCount = salesRepository.countByMemberIdForMadeList(memberId);
        int end = (int)(Math.ceil(pageNum /10.0)) * 10; //10
        int start = end - 9;
        int last = (int)(Math.ceil((double) totalCount/(double) 10.0)); // 11
        end = end < last ? end : last; // 10 < 11 ?
        start = start < 1 ? 1 : start;
        List<Integer> numList = IntStream.rangeClosed(start, end).boxed().toList();
        boolean prevPage = start > 1;
        boolean nextPage = totalCount > end* 10L;
        int prev = prevPage ? start-1 : 0;
        int next = nextPage ? end + 1 : 0; // end + 1
        Map<String, Object> result = new HashMap<>();
        result.put("problemList", problemList);
        result.put("end", end);
        result.put("start",start);
        result.put("prev",prev);
        result.put("next",next);
        result.put("numList",numList);
        return result;
    }
    @Override
    public List<?> getSalesList(Long memberId){
        List<?> result = salesRepository.findSalesListByMemberId(memberId);
        return result;
    }
    @Override
    public Boolean checkSalesOne(Long problemId, Long memberId){
        log.info(problemId);
        log.info(memberId);
//        // 사용자가 구매한 답지 리스트임
        List<Long> salesList = salesRepository.findSalesListByMemberId(memberId);
        log.info(salesList);
        for (Long pid : salesList){
            var result = salesRepository.findByProblemId(pid);
            if (result.isPresent()) {
                return true;
            }
        }
        return false;
    }
}
