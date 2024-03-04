package com.blog.service.impl;

import com.blog.entities.Comment;
import com.blog.entities.Post;
import com.blog.exception.BlogAPIException;
import com.blog.exception.ResourceNotFoundException;
import com.blog.payload.CommentDto;
import com.blog.repositories.CommentRepository;
import com.blog.repositories.PostRepository;
import com.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import static org.springframework.data.jpa.domain.AbstractPersistable_.id;
@Service
public class CommentServiceImpl implements CommentService {


    private PostRepository postRepository;
    private CommentRepository commentRepository;
    @Autowired
    private ModelMapper modelMapper;

    public CommentServiceImpl(CommentRepository commentRepository ,PostRepository postRepository) {
        this.postRepository= postRepository;
        this.commentRepository = commentRepository;
    }
    @Override
    public CommentDto saveComment(Long postId, CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("post not found with id: " + id)
        );
        Comment comment = mapToEntity(commentDto);
        comment.setPost(post);
        Comment newComment = commentRepository.save(comment);
        return mapToDto(newComment);
    }
    @Override
    public List<CommentDto> getCommentByPostId(long postId) {     //"ek id pe kitane comment hai"

        List<Comment> comments = commentRepository.findByPostId(postId);
       return comments.stream().map(comment ->mapToDto(comment) ).collect(Collectors.toList());
    }
    @Override
    public CommentDto getCommentById(long postId, long commentId) {         //postId=commentId chack

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("post not found with id: " +id)

        );

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("comment not found with id: " +commentId)
        );
      if (!comment.getPost().getId().equals(post.getId())) {
          throw new BlogAPIException("Comment does not belong to post");
      }
        return mapToDto(comment);
    }

    @Override
    public CommentDto updateComment(long postId, long id, CommentDto commentDto) {     //updatecomment
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("post not found with id: " + postId)
        );
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Comment not found with id: " +id)
        );
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException("Comment does not belong to post");
        }
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        Comment updateComment = commentRepository.save(comment);
        return mapToDto(updateComment);

    }
    @Override                                                            //delete method
    public void deleteComment(long postId, long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("post not found with id: " + postId)
        );
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment not found with id: " +id)
        );
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException("Comment does not belong to post");
        }
        commentRepository.deleteById(commentId);

    }

    Comment mapToEntity (CommentDto commentDto){
        Comment comment = modelMapper.map(commentDto, Comment.class);
        //    we use modelmapper then don't use this things

//        Comment comment= new Comment();
//        comment.setBody(commentDto.getBody());
//        comment.setEmail(commentDto.getEmail());
//        comment.setName(commentDto.getName());
        return comment;

    }
    CommentDto mapToDto(Comment comment) {
        CommentDto dto = modelMapper.map(comment, CommentDto.class);
        // we use modelmapper then don't use this things

//        CommentDto dto = new CommentDto();
//        dto.setId(comment.getId());
//        dto.setBody(comment.getBody());
//        dto.setEmail(comment.getEmail());
//        dto.setName(comment.getName());

        return dto;
    }
}
