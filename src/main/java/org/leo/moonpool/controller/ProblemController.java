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

@Log4j2
@RequiredArgsConstructor
@RequestMapping("/mp/problems")
@RestController
public class ProblemController {
    private final ProblemService problemService;
    private final FileHandler fileHandler;
    @PostMapping("/post")
    public ResponseEntity<?> post(ProblemDto problemDto){
//        List<String>  answerList =  fileHandler.saveFiles(problemDto.getAnswerFiles());
//        List<String> quizList = fileHandler.saveFiles(problemDto.getQuizFiles());
//        problemDto.setQuizFileNames(quizList);
//        problemDto.setAnswerFileNames(answerList);
        problemDto.getAnswerFiles().forEach(names->{
            log.info(names.getOriginalFilename());
        });
        problemDto.getQuizFiles().forEach(names->{
            log.info(names.getOriginalFilename());
        });
//        String result = problemService.post(problemDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }
    @GetMapping("/{category}/{pageNum}")
    public ResponseEntity<?> getList(@PathVariable("category")String category,@PathVariable("pageNum")Integer pageNum){
        var result = problemService.getList(pageNum,category);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/detail/{problemId}")
    public ResponseEntity<?> getOne(@PathVariable("problemId")Long problemId){
        var result = problemService.getOne(problemId);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/view/{fileName}")
    public ResponseEntity<Resource> getFile(@PathVariable("fileName")String fileName){
        return fileHandler.getFile(fileName);
    }
    @PostMapping("/upload")
    public ResponseEntity<?> upload(MultipartFile file){
        log.info(file);
        return null;
    }

}
