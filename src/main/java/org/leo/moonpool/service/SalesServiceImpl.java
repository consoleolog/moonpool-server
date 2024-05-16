package org.leo.moonpool.service;

import lombok.RequiredArgsConstructor;
import org.leo.moonpool.dto.AnswerDto;
import org.leo.moonpool.dto.SalesDto;
import org.leo.moonpool.dto.SalesListDto;
import org.leo.moonpool.entity.Problem;
import org.leo.moonpool.handler.CoinHandler;
import org.leo.moonpool.handler.MemberHandler;
import org.leo.moonpool.repository.ProblemRepository;
import org.leo.moonpool.repository.SalesRepository;
import org.leo.moonpool.service.impl.SalesService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Service
public class SalesServiceImpl implements SalesService {
    private final ProblemRepository problemRepository;
    private final SalesRepository salesRepository;
    private final CoinHandler coinHandler;
    private final MemberHandler memberHandler;
    @Override
    public String purchase(SalesDto salesDto) {
        Optional<Problem> result = problemRepository.findById(salesDto.getProblemId());
        Problem problem = result.orElseThrow();
        Boolean validCheck = memberHandler.validCheck(salesDto.getMemberId());
        if (!validCheck){
            return "ERROR";
        }
        salesRepository.save(salesDto.toEntity(salesDto));
        try{
            coinHandler.minus(salesDto.getMemberId(),problem.getPrice());
            coinHandler.plus(problem.getWriterId(),problem.getPrice());
        } catch (Exception e){
            return e.getMessage();
        }
        return "SUCCESS";
    }

    @Override
    public String purchaseAll(SalesListDto salesListDto) {
        SalesDto salesDto = new SalesDto();
        String coinMsg = "";
        for (Long problemId : salesListDto.getProblemIdList()) {
            salesDto.setProblemId(problemId);
            salesDto.setMemberId(salesListDto.getMemberId());
            salesRepository.save(salesDto.toEntity(salesDto));
            Optional<Problem> result = problemRepository.findById(problemId);
            Problem problem = result.orElseThrow();
            try {
                coinMsg = coinHandler.minus(salesListDto.getMemberId(), problem.getPrice());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return coinMsg;
    }

    @Override
    public String answerCheck(AnswerDto answerDto) {
        Optional<Problem> result = problemRepository.findById(answerDto.getProblemId());
        Problem problem = result.orElseThrow();
        Boolean validCheck = memberHandler.validCheck(answerDto.getMemberId());
        if (!validCheck){
            return "VALID_ERROR";
        }
        if(Objects.equals(problem.getAnswer(), answerDto.getAnswer())){
            try {
                coinHandler.plus(answerDto.getMemberId(),100);
            }catch (Exception e){
                return "ERROR";
            }
        }
        return "SUCCESS";
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
        int end = (int)(Math.ceil(pageNum.intValue()/10.0)) * 10; //10
        int start = end - 9;
        int last = (int)(Math.ceil((double) totalCount/(double) 10.0)); // 11
        end = end < last ? end : last; // 10 < 11 ?
        start = start < 1 ? 1 : start;
        List<Integer> numList = IntStream.rangeClosed(start, end).boxed().toList();
        int prev = start - 1;
        int next = end + 1; // end + 1
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
        int end = (int)(Math.ceil(pageNum.intValue()/10.0)) * 10; //10
        int start = end - 9;
        int last = (int)(Math.ceil((double) totalCount/(double) 10.0)); // 11
        end = end < last ? end : last; // 10 < 11 ?
        start = start < 1 ? 1 : start;
        List<Integer> numList = IntStream.rangeClosed(start, end).boxed().toList();
        int prev = start - 1;
        int next = end + 1; // end + 1
        Map<String, Object> result = new HashMap<>();
        result.put("problemList", problemList);
        result.put("end", end);
        result.put("start",start);
        result.put("prev",prev);
        result.put("next",next);
        result.put("numList",numList);
        return result;
    }
}
