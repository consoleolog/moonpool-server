package org.leo.moonpool.dto;

import lombok.*;
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



}
