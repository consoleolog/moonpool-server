package org.leo.moonpool.controller;

import lombok.RequiredArgsConstructor;
import org.leo.moonpool.dto.CommentDto;
import org.leo.moonpool.service.impl.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/post")
    public ResponseEntity<?> post(@RequestBody CommentDto commentDto){
        commentService.post(commentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }
}
