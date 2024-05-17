package org.leo.moonpool.problem;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.leo.moonpool.entity.Problem;
import org.leo.moonpool.handler.FileHandler;
import org.leo.moonpool.repository.ProblemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Log4j2
@RequiredArgsConstructor
@SpringBootTest
public class ProblemRepositoryTest {
    @Autowired
    private ProblemRepository problemRepository;
    @Autowired
    private FileHandler fileHandler;

    @Test
    public void testPost(){
        for (int i = 0; i < 100; i++) {
            Problem problem = Problem.builder()
                    .title("국어 문제 테스트 데이터" + i + "......")
                    .price(10+i)
                    .description("국어 문제 설명 "+i+"......")
                    .category("korean")
                    .level("hard")
                    .answer(i)
                    .writerId(1L)
                    .build();
            problem.addQuizImageString("koreanQTestImg.png");
            problem.addAnswerImageString("koreanATestImg.png");
            problemRepository.save(problem);
        }
        for (int i = 99; i < 200; i++) {
            Problem problem = Problem.builder()
                    .title("수학 문제 테스트 데이터" + i + "......")
                    .price(10+i)
                    .description("수학 문제 설명 "+i+"......")
                    .category("math")
                    .level("hard")
                    .answer(i)
                    .writerId(2L)
                    .build();
            problem.addQuizImageString("mathQTestImg.png");
            problem.addAnswerImageString("mathATestImg.png");
            problemRepository.save(problem);
        }
        for (int i = 199; i < 300; i++) {
            Problem problem = Problem.builder()
                    .title("영어 문제 테스트 데이터" + i + "......")
                    .price(10+i)
                    .description("영어 문제 설명 "+i+"......")
                    .category("english")
                    .level("hard")
                    .answer(i)
                    .writerId(3L)
                    .build();
            problem.addQuizImageString("englishQTestImg.png");
            problem.addAnswerImageString("englishATestImg.png");
            problemRepository.save(problem);
        }
    }
    @Test
    @Transactional
    public void testFind(){
//        var result = problemRepository.customeFindAll();
//        System.out.println(result);
    }
    @Test
    public void testSearch(){
        String text = "국어";
        var result = problemRepository.fullTextSearch(text,0);
        System.out.println(result);
    }
}
