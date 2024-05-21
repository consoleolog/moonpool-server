package org.leo.moonpool.sales;

import org.junit.jupiter.api.Test;
import org.leo.moonpool.repository.SalesRepository;
import org.leo.moonpool.service.impl.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class SalesServiceTest {
    @Autowired
    private SalesService salesService;
    @Autowired
    private SalesRepository salesRepository;

    @Test
    public void findTest(){
        Long problemId = 2L;
        var result = salesRepository.findByProblemId(problemId);
        System.out.println(result);
    }
    @Test
    public void findListTest(){
        List<Long> salesList = new ArrayList<>();
        salesList.add(1L);
        salesList.add(2L);
        salesList.add(3L);
        salesList.add(4L);
        for (Long pid : salesList){
            var result = salesRepository.findByProblemId(pid);
            if (result.isPresent()) {
                System.out.println(true);
                break;
            }
        }
        System.out.println(false);
    }
}
