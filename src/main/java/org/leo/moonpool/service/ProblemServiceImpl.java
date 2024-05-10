package org.leo.moonpool.service;

import lombok.RequiredArgsConstructor;
import org.leo.moonpool.dto.ProblemDto;
import org.leo.moonpool.entity.Problem;
import org.leo.moonpool.repository.ProblemRepository;
import org.leo.moonpool.service.impl.ProblemService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProblemServiceImpl implements ProblemService {
    private final ProblemRepository problemRepository;
    @Override
    public String post(ProblemDto problemDto) {
        Problem problem = problemRepository.save(problemToEntity(problemDto));
        return problem.getCategory();
    }
    @Override
    public String modify(ProblemDto problemDto) {
        Optional<Problem> result = problemRepository.findById(problemDto.getProblemId());
        if (result.isPresent()){
            Problem problem = result.get();
            problem.changeTitle(problemDto.getTitle());
            problem.changePrice(problemDto.getPrice());
            problem.changeDescription(problemDto.getDescription());
            problem.changeCategory(problemDto.getCategory());
            problem.changeAnswer(problemDto.getAnswer());
            problem.changeLevel(problemDto.getLevel());

        }
        return null;
    }

    @Override
    public Boolean delete(Long problemId) {
        return null;
    }

    @Override
    public Problem getOne(Long problemId) {
        Optional<Problem> result = problemRepository.findById(problemId);
        Problem problem =result.orElseThrow();
        return problem;
    }

    @Override
    public Map<String, Object> getList(Integer pageNum, String category) {
        return null;
    }
}
