package com.myblog.service;

import com.myblog.payload.PostDto;
import com.myblog.payload.PostResponse;

public interface PostService {

    PostDto createPost(PostDto postDto);

    void deletePostById(long id);

    PostDto getPostById(long id);


    PostDto updatePost(PostDto postDto, long id);
   // List<PostDto> getAllPost();//Without Pagination

    //With Pagination
    PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir);

}
