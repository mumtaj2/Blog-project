package com.blog.payload;

import lombok.Data;

@Data
public class CommentDto {

    private long id;
    private String name;
    private  String Email;
    private String body;
    private long postId;

}
