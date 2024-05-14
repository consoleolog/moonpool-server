package org.leo.moonpool.dto;

import lombok.*;
import org.leo.moonpool.entity.Problem;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class ProblemDto {

    private Long problemId;

    private String title;

    private Integer price;

    private String description;

    private String category;

    private String level;

    private Integer answer;

    private Long writerId;

    @Builder.Default
    private List<MultipartFile> quizFiles = new ArrayList<>();

    @Builder.Default
    private List<MultipartFile> answerFiles = new ArrayList<>();

    @Builder.Default
    private List<String> quizFileNames = new ArrayList<>();

    @Builder.Default
    private List<String> answerFileNames = new ArrayList<>();

    public Problem toEntity(ProblemDto problemDto){
         Problem problem = Problem.builder()
                .title(problemDto.getTitle())
                .price(problemDto.getPrice())
                .description(problemDto.getDescription())
                .category(problemDto.getCategory())
                .level(problemDto.getLevel())
                .answer(problemDto.getAnswer())
                .writerId(problemDto.getWriterId())
                .build();
        List<String> quizNames = problemDto.getQuizFileNames();
        List<String> answerNames = problemDto.getAnswerFileNames();
        if(quizNames == null || quizNames.isEmpty()||answerNames == null || answerNames.isEmpty()){
            return problem;
        }
        quizNames.forEach(names -> {
            problem.addQuizImageString(names);
        });
        answerNames.forEach(names -> {
            problem.addAnswerImageString(names);
        });
        return problem;
    }

}
