package org.leo.moonpool.service.impl;

import jakarta.transaction.Transactional;
import org.leo.moonpool.dto.CommentDto;

import java.util.Map;

@Transactional
public interface CommentService {

    Map<String, Object> getList(Long problemId, Integer pageNum);

    void post(CommentDto commentDto);

}
