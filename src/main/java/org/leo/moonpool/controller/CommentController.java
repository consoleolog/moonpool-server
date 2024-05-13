package org.leo.moonpool.controller;

import lombok.RequiredArgsConstructor;
import org.leo.moonpool.service.impl.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/mp/comments")
@RestController
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/{problemId}/{pageNum}")
    public ResponseEntity<?> getList(@PathVariable("problemId")Long problemId,
                                     @PathVariable("pageNum")Integer pageNum){
        var result = commentService.getList(problemId,pageNum);
        return ResponseEntity.ok(result);
    }
}
