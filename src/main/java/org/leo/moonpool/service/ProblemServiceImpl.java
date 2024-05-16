package org.leo.moonpool.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.leo.moonpool.dto.ApiUser;
import org.leo.moonpool.dto.ProblemDto;
import org.leo.moonpool.entity.Problem;
import org.leo.moonpool.handler.FileHandler;
import org.leo.moonpool.handler.MemberHandler;
import org.leo.moonpool.repository.ProblemRepository;
import org.leo.moonpool.service.impl.ProblemService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.IntStream;
@Log4j2
@RequiredArgsConstructor
@Service
public class ProblemServiceImpl implements ProblemService {
    private final ProblemRepository problemRepository;
    private final FileHandler fileHandler;
    private final MemberHandler memberHandler;
    @Override
    public String post(ProblemDto problemDto) {
        log.info("------------ post service start  -------------");
        log.info(problemDto);
        log.info(problemDto.getWriterId());
        ApiUser a =(ApiUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info(a.getMemberId());

//        Boolean validCheck = memberHandler.validCheck(problemDto.getWriterId());
//        if (!validCheck){
//            return "VALID_ERROR";
//        }
        if(problemDto.getTitle().isEmpty()){
            return "Title_Blank";
        } else if (problemDto.getPrice().toString().isEmpty()) {
            return "Price_Blank";
        } else if (problemDto.getDescription().isEmpty()) {
            return "Description_Blank";
        } else if (problemDto.getCategory().isEmpty()) {
            return "Category_Blank";
        } else if (problemDto.getAnswer().toString().isEmpty()) {
            return "Answer_Blank";
        }
        Problem result = problemRepository.save(problemDto.toEntity(problemDto));
        return result.getCategory();
//        return null;
    }
    @Override
    public String modify(ProblemDto problemDto) {
        Boolean validCheck = memberHandler.validCheck(problemDto.getWriterId());
        if (!validCheck){
            return "VALID_ERROR";
        }
        Optional<Problem> result = problemRepository.findById(problemDto.getProblemId());
        if (result.isPresent()){
            Problem problem = result.get();
            problem.changeTitle(problemDto.getTitle());
            problem.changePrice(problemDto.getPrice());
            problem.changeDescription(problemDto.getDescription());
            problem.changeCategory(problemDto.getCategory());
            problem.changeAnswer(problemDto.getAnswer());
            problem.changeLevel(problemDto.getLevel());
            List<String> quizNames = problemDto.getQuizFileNames();
            List<String> answerNames = problemDto.getAnswerFileNames();
            problem.clearAnswerList();
            problem.clearQuizList();
            if(quizNames == null || quizNames.isEmpty()||answerNames == null || answerNames.isEmpty()){
                problemRepository.save(problem);
                return "SUCCESS";
            }
            quizNames.forEach(names->{
                problem.addQuizImageString(names);
            });
            answerNames.forEach(names->{
                problem.addAnswerImageString(names);
            });
            problemRepository.save(problem);
        }
        return "SUCCESS";
    }

    @Override
    public String delete(Long problemId,Long memberId) {
        Boolean validCheck = memberHandler.validCheck(memberId);
        if(!validCheck){
            return "VALID_ERROR";
        }
        Optional<Problem> result = problemRepository.findById(problemId);
        if (result.isPresent()){
            Problem problem = result.get();
            problem.changeDelFlag();
            problemRepository.save(problem);
        }
        return "SUCCESS";
    }

    @Override
    public ProblemDto getOne(Long problemId) {
        Optional<Problem> result = problemRepository.findById(problemId);
        Problem problem =result.orElseThrow();
        return problem.toDto(problem);
    }

    @Override
    public Map<String, Object> getList(Integer pageNum, String category) {
        // 0이 들어가면 0 1이 들어가면 10 2가 들어가면 20
        // 1 이 들어갔을 때는 0이 들어간것처럼 하면 되고 2가 들어갔을 때는 10이 들어갔을때 처럼 하면됨
        List<?> problemList = problemRepository.customeFindAll(category,(pageNum - 1) * 10 );
        Long totalCount = problemRepository.countByCategory(category); // 101
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
