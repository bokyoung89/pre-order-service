package com.bokyoung.preorderservice.controller;

import com.bokyoung.preorderservice.controller.request.PostCreateReqeust;
import com.bokyoung.preorderservice.controller.request.PostModifyRequest;
import com.bokyoung.preorderservice.controller.response.PostResponse;
import com.bokyoung.preorderservice.controller.response.Response;
import com.bokyoung.preorderservice.model.Post;
import com.bokyoung.preorderservice.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public Response<Void> create(@RequestBody PostCreateReqeust reqeust, Authentication authentication) {
        postService.create(reqeust.getTitle(), reqeust.getContent(), authentication.getName());
        return Response.success();
    }

    @PutMapping("/{postId}")
    public Response<PostResponse> modify(@PathVariable(name = "postId") Long postId, @RequestBody PostModifyRequest reqeust, Authentication authentication) {
        Post post = postService.modify(reqeust.getTitle(), reqeust.getContent(), authentication.getName(), postId);
        return Response.success(PostResponse.fromPost(post));
    }

    @DeleteMapping("/{postId}")
    public Response<Void> delete(@PathVariable(name = "postId") Long postId, Authentication authentication) {
        postService.delete(authentication.getName(), postId);
        return Response.success();
    }

    @GetMapping("/list")
    public Response<Page<PostResponse>> list(Pageable pageable) {
        return Response.success(postService.list(pageable).map(PostResponse::fromPost));
    }
    @GetMapping("/my")
    public Response<Page<PostResponse>> my(Pageable pageable, Authentication authentication) {
        return Response.success(postService.my(authentication.getName(), pageable).map(PostResponse::fromPost));
    }

    @PostMapping("/{postId}/likes")
    public Response<Void> like(@PathVariable(name = "postId") Long postId, Authentication authentication) {
        postService.like(postId, authentication.getName());
        return Response.success();
    }

    @GetMapping("/{postId}/likes")
    public Response<Integer> likeCount(@PathVariable(name = "postId") Long postId, Authentication authentication) {
        return Response.success(postService.likeCount(postId));
    }
}
