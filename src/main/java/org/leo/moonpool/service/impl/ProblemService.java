package org.leo.moonpool.service.impl;

import jakarta.transaction.Transactional;
import org.leo.moonpool.dto.ProblemDto;

import java.util.Map;

@Transactional
public interface ProblemService {

    String post(ProblemDto problemDto);

    String modify(ProblemDto problemDto);

    String delete(Long problemId,Long memberId);

    ProblemDto getOne(Long problemId);

    Map<String, Object> getList(Integer pageNum, String category);



}
