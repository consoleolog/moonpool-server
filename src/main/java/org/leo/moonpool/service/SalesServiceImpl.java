package org.leo.moonpool.service;

import lombok.RequiredArgsConstructor;
import org.leo.moonpool.dto.AnswerDto;
import org.leo.moonpool.dto.SalesDto;
import org.leo.moonpool.dto.SalesListDto;
import org.leo.moonpool.entity.Problem;
import org.leo.moonpool.entity.Sales;
import org.leo.moonpool.handler.CoinHandler;
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
    @Override
    public String purchase(SalesDto salesDto) {
        Optional<Problem> result = problemRepository.findById(salesDto.getProblemId());
        Problem problem = result.orElseThrow();
        try {
            coinHandler.minus(salesDto.getMemberId(),problem.getPrice());
            Sales sales = Sales.builder()
                    .problem(problem)
                    .memberId(salesDto.getMemberId())
                    .build();
            salesRepository.save(sales);
        } catch (Exception e){
            return "ERROR";
        }
        return "SUCCESS";
    }

    @Override
    public String purchaseAll(SalesListDto salesListDto) {

        return null;
    }

    @Override
    public String answerCheck(AnswerDto answerDto) {
        Optional<Problem> result = problemRepository.findById(answerDto.getProblemId());
        Problem problem = result.orElseThrow();
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
