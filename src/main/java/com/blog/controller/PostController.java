package com.blog.controller;

import com.blog.payload.PostDto;
import com.blog.payload.PostResponse;
import com.blog.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/posts")
public class PostController {


    private PostService postService;
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createPost(@Valid @RequestBody PostDto postDto,              //validation
                                              BindingResult result){

        if (result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError()
                    .getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

        PostDto Dto = postService.createPost(postDto);

        return new ResponseEntity<>(Dto, HttpStatus.CREATED);
    }

    // http:localhost:8080/api/posts?pageNo=0&pageSize=3&sortBy=title&sortDir=desc
     @GetMapping                                  // @GetMapping ka mtlb ye ki sahi url ko pakdhana or use json me convert karna
     public PostResponse getAllPosts(

             @RequestParam(value = "pageNo",defaultValue="0", required = false) int pageNo,
             @RequestParam(value= "pageSize", defaultValue="5", required = false)int pageSize,
             @RequestParam(value= "sortBy", defaultValue="id", required = false)String sortBy,
             @RequestParam(value= "sortDir", defaultValue="asc", required = false)String sortDir
     ){
        // List<PostDto> collect =

     PostResponse postResponse= postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
    return postResponse;
    }
   // http:localhost:8080/api/posts/{id}
     @GetMapping("/{id}")
     public ResponseEntity<PostDto> getPostById(@PathVariable("id") long id ){
       PostDto dto = postService.getPostById(id);
       return new ResponseEntity<>(dto,HttpStatus.OK);
     }
    //http:localhost:8080/api/posts/{id}
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
     public ResponseEntity<PostDto>  updatePost(@RequestBody PostDto postDto, @PathVariable("id") long id){
         PostDto dto = postService.updatePost(postDto, id);
         return new ResponseEntity<>(dto,HttpStatus.OK);
         }
        //http:localhost:8080/api/posts/{id}
        @PreAuthorize("hasRole('ADMIN')")
        @DeleteMapping("/{id}")
         public  ResponseEntity<String> deletePost(@PathVariable("id") long id ){
             postService.deletePost(id);
             return new ResponseEntity<>("Post is deleted", HttpStatus.OK);
         }
    }


