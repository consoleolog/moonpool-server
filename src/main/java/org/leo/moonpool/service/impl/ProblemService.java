package org.leo.moonpool.service.impl;

import jakarta.transaction.Transactional;
import org.leo.moonpool.dto.ProblemDto;
import org.leo.moonpool.entity.Problem;

import java.util.Map;

@Transactional
public interface ProblemService {

    String post(ProblemDto problemDto);

    String modify(ProblemDto problemDto);

    Boolean delete(Long problemId);

    Problem getOne(Long problemId);

    Map<String, Object> getList(Integer pageNum, String category);



}
