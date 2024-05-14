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

import java.util.Objects;
import java.util.Optional;

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
        salesListDto.getProblemIdList().forEach(problemId->{
            salesDto.setProblemId(problemId);
            salesDto.setMemberId(salesListDto.getMemberId());
            salesRepository.save(salesDto.toEntity(salesDto));
            Optional<Problem> result = problemRepository.findById(problemId);
            Problem problem = result.orElseThrow();
            try {
                coinHandler.minus(salesListDto.getMemberId(), problem.getPrice());
            } catch (Exception e){
                throw new RuntimeException(e);
            }
        });
        return "SUCCESS";
    }

    @Override
    public String answerCheck(AnswerDto answerDto) {
        Optional<Problem> result = problemRepository.findById(answerDto.getProblemId());
        Problem problem = result.orElseThrow();
        Boolean validCheck = memberHandler.validCheck(answerDto.getMemberId());
        if (!validCheck){
            return "ERROR";
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
}
