package org.leo.moonpool.service.impl;

import jakarta.transaction.Transactional;
import org.leo.moonpool.dto.ProblemDto;
import org.leo.moonpool.entity.Problem;

import java.util.List;
import java.util.Map;

@Transactional
public interface ProblemService {

    String post(ProblemDto problemDto);

    String modify(ProblemDto problemDto);

    Boolean delete(Long problemId);

    Problem getOne(Long problemId);

    Map<String, Object> getList(Integer pageNum, String category);


    default Problem problemToEntity(ProblemDto problemDto){
        Problem problem = Problem.builder()
                .title(problemDto.getTitle())
                .price(problemDto.getPrice())
                .description(problemDto.getDescription())
                .category(problemDto.getCategory())
                .level(problemDto.getLevel())
                .writerId(problemDto.getWriterId())
                .build();
        List<String> quizFileNames = problemDto.getQuizFileNames();
        List<String> answerFileNames = problemDto.getAnswerFileNames();
        if(quizFileNames == null || quizFileNames.isEmpty() || answerFileNames == null || answerFileNames.isEmpty()){
            return  problem;
        }
        quizFileNames.forEach(fileName->{
            problem.addQuizImgString(fileName);
        });
        answerFileNames.forEach(fileName -> {
            problem.addAnswerImgString(fileName);
        });
        return problem;
    }
}
