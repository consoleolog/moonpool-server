package org.leo.moonpool.service;

import lombok.RequiredArgsConstructor;
import org.leo.moonpool.dto.CommentDto;
import org.leo.moonpool.repository.CommentRepository;
import org.leo.moonpool.service.impl.CommentService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    @Override
    public Map<String, Object> getList(Long problemId,Integer pageNum) {
        List<?> commentList = commentRepository.customFindAll(problemId,(pageNum-1)*10);
        Long totalCount = commentRepository.countByProblemId(problemId);
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
        result.put("commentList",commentList);
        result.put("end", end);
        result.put("start",start);
        result.put("prev",prev);
        result.put("next",next);
        result.put("numList",numList);
        return result;
    }

    @Override
    public void post(CommentDto commentDto) {
        commentRepository.save(commentDto.toEntity(commentDto));
    }
}
