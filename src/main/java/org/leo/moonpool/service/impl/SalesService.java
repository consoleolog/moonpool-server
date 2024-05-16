package org.leo.moonpool.service.impl;

import jakarta.transaction.Transactional;
import org.leo.moonpool.dto.AnswerDto;
import org.leo.moonpool.dto.SalesDto;
import org.leo.moonpool.dto.SalesListDto;

import java.util.Map;

@Transactional
public interface SalesService {

    String purchase(SalesDto salesDto);

    String purchaseAll(SalesListDto salesListDto);

    String answerCheck(AnswerDto answerDto);

    String alreadyPurchase(SalesDto salesDto);

    Map<String, Object> getPurchasedItem(Long memberId, Integer pageNum);

    Map<String, Object> getMadeList(Long memberId, Integer pageNum);
}
