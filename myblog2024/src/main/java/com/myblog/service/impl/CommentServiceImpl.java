package com.myblog.service.impl;

import com.myblog.entity.Comment;
import com.myblog.entity.Post;
import com.myblog.exception.ResourceNotFound;
import com.myblog.payload.CommentDto;
import com.myblog.repository.CommentRepository;
import com.myblog.repository.PostRepository;
import com.myblog.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;

    private PostRepository postRepo;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepo) {
        this.commentRepository = commentRepository;
        this.postRepo = postRepo;
    }

    @Override
    public CommentDto  createComment(long postId, CommentDto commentDto) {
        Post post = postRepo.findById(postId).orElseThrow(
                () -> new ResourceNotFound("Post not found" + postId));
Comment comment = mapToEntity(commentDto);
comment.setPost(post);
        Comment c = commentRepository.save(comment);
        return mapToDto(c);
    }

    @Override
    public List<CommentDto> getCommentsbyPostId(long postId) {
        List<Comment> comments = commentRepository.getCommentByPostId(postId);
        List<CommentDto> dtos = comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
        return dtos;
    }

    @Override
    public CommentDto updateComment(long commentId, CommentDto commentDto) {
        Comment com = commentRepository.findById(commentId).get();
        Post post = postRepo.findById(com.getPost().getId()).get();
        Comment comment = mapToEntity(commentDto);
        comment.setId(commentId);
        comment.setPost(post);
        Comment c = commentRepository.save(comment);
        CommentDto dto = mapToDto(c);
        return dto;
    }

    Comment mapToEntity(CommentDto dto){
        Comment comment = new Comment ();
        comment.setName(dto.getName());
        comment.setEmail(dto.getEmail());
        comment.setBody(dto.getBody());
        return comment;
    }

    CommentDto mapToDto(Comment comment){
        CommentDto dto = new CommentDto ();
        dto.setName(comment.getName());
        dto.setEmail(comment.getEmail());
        dto.setBody(comment.getBody());
        return dto;
    }

}
