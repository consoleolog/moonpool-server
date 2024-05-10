package org.leo.moonpool.entity;

import jakarta.persistence.*;
import lombok.*;

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

    @ElementCollection
    @Builder.Default
    @ToString.Exclude
    private List<Quiz> quizList = new ArrayList<>();

    @ElementCollection
    @Builder.Default
    @ToString.Exclude
    private List<Answer> answerList = new ArrayList<>();

    private Integer answer;

//    @ToString.Exclude
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "writer_id",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
//    private Member member;

    private Long writerId;


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

    @Builder.Default
    private Boolean delFlag = false;

    public void addQuiz(Quiz quizImgName){
        quizList.add(quizImgName);
    }
    public void addQuizImgString(String quizImgName){
        Quiz quizImg = Quiz.builder()
                .quizName(quizImgName)
                .build();
        addQuiz(quizImg);
    }
    public void addAnswer(Answer answerImgName){
        answerList.add(answerImgName);
    }
    public void addAnswerImgString(String answerImgName){
        Answer answerImg = Answer.builder()
                .answerName(answerImgName)
                .build();
        addAnswer(answerImg);
    }



}
