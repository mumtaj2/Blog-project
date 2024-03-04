package com.blog.payload;

import lombok.Data;

import java.util.List;
@Data
public class PostResponse {

    private List<PostDto> content;
    private  int pageNo;
    private int pageSize;
    private long titleElements;
    private int totalPage;
    private boolean isLast;


    }

