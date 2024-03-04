package com.blog.controller;


import com.blog.payload.CommentDto;
import com.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    //http://localhost:8080/api/posts/1/comments
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(
            @PathVariable("postId") long postId,
            @RequestBody CommentDto commentDto
    ){
        CommentDto dto = commentService.saveComment(postId, commentDto);


        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
    //http://localhost:8080/api/posts/1/comments      "ek id pe kitane comment hai"
    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto>getCommentByPostId(@PathVariable("postId") long postId){
        return commentService.getCommentByPostId(postId);

    }
    //http://localhost:8080/api/posts/1/comments/1                   chack by postId= commentOd ye or not
    @GetMapping("/posts/{postId}/comments/{commentId}")
    public  ResponseEntity<CommentDto> getCommentById(@PathVariable("postId") long postId,@PathVariable("commentId") long commentId){
        CommentDto dto = commentService.getCommentById(postId, commentId);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }
    //http://localhost:8080/api/posts/{postId}/comment/{id}                   update method
    @PutMapping("/posts/{postId}/comment/{id}")
    public ResponseEntity<CommentDto>updateComment(@PathVariable("postId") long postId,
                                                   @PathVariable("id") long Id,
                                                   @RequestBody CommentDto commentDto){
       CommentDto dto= commentService.updateComment(postId,Id,commentDto);
       return new ResponseEntity<>(dto,HttpStatus.OK);
    }
    //http://localhost:8080/api/posts/{postId}/comments/{id}                     deleteed method
    @DeleteMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<String>deleteComment(@PathVariable("postId")long postId, @PathVariable("id") long id){
        commentService.deleteComment(postId,id);
        return new ResponseEntity<>("Comment is deleted sccessfully", HttpStatus.OK);
    }

    }

