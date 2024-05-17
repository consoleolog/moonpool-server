package org.leo.moonpool.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.leo.moonpool.dto.ProblemDto;
import org.leo.moonpool.handler.FileHandler;
import org.leo.moonpool.service.impl.ProblemService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
@RestController
public class ProblemController {
    private final ProblemService problemService;
    private final FileHandler fileHandler;
    @PostMapping("/mp/login/problems-user/post")
    public ResponseEntity<?> post(ProblemDto problemDto){
        problemDto.setQuizFileNames(fileHandler.saveFiles(problemDto.getQuizFiles()));
        problemDto.setAnswerFileNames(fileHandler.saveFiles(problemDto.getAnswerFiles()));
        String result = problemService.post(problemDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
    @PostMapping("mp/login/problems-user/modify")
    public ResponseEntity<String> modify(ProblemDto problemDto){
        if (!problemDto.getAnswerFiles().isEmpty()){
            List<String> answerList = fileHandler.saveFiles(problemDto.getAnswerFiles());
            problemDto.setAnswerFileNames(answerList);
        }
        if (!problemDto.getQuizFiles().isEmpty()){
            List<String> quizList = fileHandler.saveFiles(problemDto.getQuizFiles());
            problemDto.setQuizFileNames(quizList);
        }
        log.info(problemDto);
        String result = problemService.modify(problemDto);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/mp/problems/{category}/{pageNum}")
    public ResponseEntity<?> getList(@PathVariable("category")String category,@PathVariable("pageNum")Integer pageNum){
        var result = problemService.getList(pageNum,category);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/mp/problems/{pageNum}/")
    public ResponseEntity<Map<String,Object>> search(@PathVariable("pageNum")Integer pageNum,
                                                     @RequestParam("searchText") String searchText){
        Map<String, Object> result = problemService.search(searchText,pageNum);
        return ResponseEntity.ok(result);
    }
    @DeleteMapping("/mp/login/problems-user/delete/{problemId}")
    public ResponseEntity<?> delete(@PathVariable("problemId")Long problemId,
                                    @RequestParam("memberId")Long memberId){
        String result = problemService.delete(problemId,memberId);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/mp/problems/detail/{problemId}")
    public ResponseEntity<?> getOne(@PathVariable("problemId")Long problemId){
        var result = problemService.getOne(problemId);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/mp/problems/view/{fileName}")
    public ResponseEntity<Resource> getFile(@PathVariable("fileName")String fileName){
        return fileHandler.getFile(fileName);
    }

}
