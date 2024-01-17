

package com.myblog.controller;

import com.myblog.payload.PostDto;
import com.myblog.payload.PostResponse;
import com.myblog.service.PostService;
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

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createPost(@Valid @RequestBody PostDto postDto, BindingResult result){
        if(result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

        PostDto dto = postService.createPost(postDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")//This Employess only admin can access this
    public ResponseEntity<String> deletePostById(@PathVariable long id){
        postService.deletePostById(id);
        return  new ResponseEntity<>("Post is Deleted",HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public  ResponseEntity<PostDto> getPostById(@PathVariable long id){
        PostDto postDto = postService.getPostById(id);
        return new ResponseEntity<>(postDto,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PostDto> updatePostById( @RequestBody PostDto postDto ,@PathVariable long id) {
        PostDto dto =  postService.updatePost(postDto,id);

        return new ResponseEntity<>(dto,HttpStatus.OK);
    }
    //1st Way
    //@GetMapping
//    public List<PostDto> getAllPosts(){
//        List<PostDto> postDtos = postService.getAllPost();
//        return postDtos;
    // 2 nd way to Do the Same Thing }
//@GetMapping
//    public ResponseEntity<List<PostDto>> getAllPosts(){
//        List<PostDto> postDtos = postService.getAllPost();
//       return new  ResponseEntity<>(postDtos,HttpStatus.OK);
//    }
//
    //Pagination Starts From Here Sir Has Done all things in One Code i will do multiple Times
    //http:localhost/8080/api/posts?pageno=0 because of Param its question MArk
    //http//:localhost:8080/api/posts?pageno=0&pageSize=3
//@GetMapping  Pagination With PageNo and pageSize
//    public ResponseEntity<List<PostDto>> getAllPosts(
//            @RequestParam(name = "pageNo",required = false,defaultValue = "0")int pageno,// even if i dont give pageno after ? then also it will load pageno 0
//            @RequestParam(name = "pageSize",required = false,defaultValue = "5") int pageSize)
//{
//    List<PostDto> postDtos = postService.getAllPost(pageno, pageSize);
//    return new ResponseEntity<>(postDtos,HttpStatus.OK);
//
//}
//http://localhost:8080/api/posts?pageno=0&pageSize=3&sortBy=description&sortDir=desc
    //Pagination with pageNo, pageSize, Sorting and SortDir
    @GetMapping
    public PostResponse getAllPosts(
            @RequestParam(name = "pageNo",required = false,defaultValue = "0")int pageNo,// even if i dont give pageno after ? then also it will load pageno 0
            @RequestParam(name = "pageSize",required = false,defaultValue = "5") int pageSize,
            @RequestParam(value = "sortBy",required = false,defaultValue = "id") String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir)

    {
        PostResponse response = postService.getAllPost(pageNo, pageSize,sortBy,sortDir);
        return response;

    }
}

