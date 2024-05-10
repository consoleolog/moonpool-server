package org.leo.moonpool.service.impl;

import jakarta.transaction.Transactional;
import org.leo.moonpool.dto.AnswerDto;
import org.leo.moonpool.dto.SalesDto;
import org.leo.moonpool.dto.SalesListDto;

@Transactional
public interface SalesService {

    String purchase(SalesDto salesDto);

    String purchaseAll(SalesListDto salesListDto);

    String answerCheck(AnswerDto answerDto);

}
