package com.blog.service;

import com.blog.exception.BlogAPIException;
import com.blog.payload.CommentDto;

import java.util.List;


public interface CommentService {
    CommentDto saveComment(Long postId, CommentDto commentDto);
    List<CommentDto> getCommentByPostId(long postId);       //"ek id pe kitane comment hai"

    CommentDto getCommentById(long postId, long commentId) ;

    CommentDto updateComment(long postId, long id, CommentDto commentDto);

    void deleteComment(long postId, long commentId);
}
