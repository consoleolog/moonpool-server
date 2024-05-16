package org.leo.moonpool.entity;

import jakarta.persistence.*;
import lombok.*;
import org.leo.moonpool.dto.ProblemDto;

import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Entity
public class Problem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long problemId;

    @Column(length = 50)
    private String title;

    private Integer price;

    @Column(length = 150)
    private String description;

    @Column(length = 10)
    private String category;

    @Column(length = 10)
    private String level;

    private Integer answer;

    private Long writerId;


    @Builder.Default
    private Boolean delFlag = false;

    @ToString.Exclude
    @ElementCollection
    @Builder.Default
    private List<QuizImage> quizList = new ArrayList<>();

    @ToString.Exclude
    @ElementCollection
    @Builder.Default
    private List<AnswerImage> answerList = new ArrayList<>();

    public void changeTitle(String title) {
        this.title = title;
    }

    public void changePrice(Integer price) {
        this.price = price;
    }

    public void changeDescription(String description) {
        this.description = description;
    }

    public void changeCategory(String category) {
        this.category = category;
    }

    public void changeLevel(String level) {
        this.level = level;
    }

    public void changeAnswer(Integer answer) {
        this.answer = answer;
    }

    public void changeDelFlag(){
         this.delFlag = true;
    }

    public ProblemDto toDto(Problem problem){
        ProblemDto problemDto = ProblemDto.builder()
                .problemId(problem.getProblemId())
                .title(problem.getTitle())
                .price(problem.getPrice())
                .description(problem.getDescription())
                .category(problem.getCategory())
                .level(problem.getLevel())
                .answer(problem.getAnswer())
                .writerId(problem.getWriterId())
                .build();
        List<QuizImage> quizList = problem.getQuizList();
        if (quizList == null || quizList.isEmpty()){
            return problemDto;
        }
        List<String> quizNameList = quizList.stream().map(QuizImage::getQuizName).toList();
        problemDto.setQuizFileNames(quizNameList);
        List<String> answerNameList =answerList.stream().map(AnswerImage::getAnswerName).toList();
        problemDto.setAnswerFileNames(answerNameList);
        return problemDto;
    }

    public void clearQuizList(){
        this.quizList.clear();
    }
    public void clearAnswerList(){
        this.answerList.clear();
    }

    public void addQuizImageString(String fileName){
        QuizImage quizImage = QuizImage.builder()
                .quizName(fileName)
                .build();
        quizList.add(quizImage);
    }
    public void addAnswerImageString(String fileName){
        AnswerImage answerImage = AnswerImage.builder()
                .answerName(fileName)
                .build();
        answerList.add(answerImage);
    }
}
