package org.leo.moonpool.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.leo.moonpool.service.impl.ProblemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/mp/problems")
@RestController
public class ProblemController {
    private final ProblemService problemService;

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

    @PostMapping("/upload")
    public ResponseEntity<?> upload(MultipartFile file){
        log.info(file);
        return null;
    }

}
