package com.myblog.service.impl;

import com.myblog.entity.Post;
import com.myblog.exception.ResourceNotFound;
import com.myblog.payload.PostDto;
import com.myblog.payload.PostResponse;
import com.myblog.repository.PostRepository;
import com.myblog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepo;

   private ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepo,ModelMapper modelMapper)//COnstructor based Injection or AutoWired CAn be USed
    {
        this.postRepo = postRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public PostDto createPost(PostDto postDto){
       Post post = mapToEntity(postDto);
        Post savedPost = postRepo.save(post);
        PostDto dto = mapToDto(savedPost);
        return dto;
    }

    @Override
    public void deletePostById(long id) {
        postRepo.findById(id).orElseThrow(
                ()->new ResourceNotFound("Post Not Found With Id"+id)
        );
        postRepo.deleteById(id);

    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepo.findById(id).orElseThrow(() -> new ResourceNotFound("Post not Found")
        );
        return mapToDto(post);
    }



    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        Post post = postRepo.findById(id).orElseThrow(
                () -> new RuntimeException("Post not found by Id" + id)
        );
       post.setTitle(postDto.getTitle());
       post.setDescription(postDto.getDescription());
       post.setContent(postDto.getContent());

        Post savedPost= postRepo.save(post);
        PostDto dto = mapToDto(savedPost);
        return dto;
    }



//    @Override
//    public List<PostDto> getAllPost() {
//        List<Post> posts = postRepo.findAll();
//        List<PostDto> dtos = posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
//        return dtos;
//    }
    //Repeating above method
@Override
public PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir) {
    Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
    Pageable pageable = PageRequest.of(pageNo,pageSize,sort);//Static Method
    Page<Post> pagePostObjects = postRepo.findAll(pageable);
    List<Post> posts =pagePostObjects.getContent();
    List<PostDto> dtos = posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
    PostResponse response = new PostResponse();
    response.setDto(dtos);
    response.setPageNo(pagePostObjects.getNumber());
    response.setTotalPages(pagePostObjects.getTotalPages());
    response.setLastPage(pagePostObjects.isLast());
    response.setPageSize(pagePostObjects.getSize());

    return response;
}

    Post mapToEntity (PostDto postDto){
     //   Post post = new Post();
        Post post = modelMapper.map(postDto, Post.class);
//        post.setId(postDto.getId());
//        post.setTitle(postDto.getTitle());
//        post.setContent(postDto.getContent());
//        post.setDescription(postDto.getDescription());
        return post;

    }

    PostDto mapToDto(Post savedPost){
       // PostDto postDto = new PostDto();
        PostDto postDto = modelMapper.map(savedPost, PostDto.class);
//        postDto.setId(savedPost.getId());
//        postDto.setTitle(savedPost.getTitle());
//        postDto.setContent(savedPost.getContent());
//        postDto.setDescription(savedPost.getDescription());
        return postDto;

    }
}
