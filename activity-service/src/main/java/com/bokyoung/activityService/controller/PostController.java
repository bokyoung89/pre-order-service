package com.bokyoung.activityService.controller;

import com.bokyoung.activityService.controller.request.PostCommentRequest;
import com.bokyoung.activityService.controller.request.PostCreateReqeust;
import com.bokyoung.activityService.controller.request.PostModifyRequest;
import com.bokyoung.activityService.controller.response.CommentResponse;
import com.bokyoung.activityService.controller.response.PostResponse;
import com.bokyoung.activityService.controller.response.Response;
import com.bokyoung.activityService.model.Post;
import com.bokyoung.activityService.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/activity-service/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public Response<Void> create(@RequestBody PostCreateReqeust reqeust,
                                 @RequestHeader(name = "principalId") Long principalId) {
        postService.create(reqeust.getTitle(), reqeust.getContent(), principalId);
        return Response.success();
    }

    @PutMapping("/{postId}")
    public Response<PostResponse> modify(@PathVariable(name = "postId") Long postId,
                                         @RequestBody PostModifyRequest reqeust,
                                         @RequestHeader(name = "principalId") Long principalId) {
        Post post = postService.modify(reqeust.getTitle(), reqeust.getContent(), principalId, postId);
        return Response.success(PostResponse.fromPost(post));
    }

    @DeleteMapping("/{postId}")
    public Response<Void> delete(@PathVariable(name = "postId") Long postId,
                                 @RequestHeader(name = "principalId") Long principalId) {
        postService.delete(principalId, postId);
        return Response.success();
    }

    @GetMapping("/list")
    public Response<Page<PostResponse>> list(Pageable pageable) {
        return Response.success(postService.list(pageable).map(PostResponse::fromPost));
    }

    @GetMapping("/my")
    public Response<Page<PostResponse>> my(Pageable pageable,
                                           @RequestHeader(name = "principalId") Long principalId) {
        return Response.success(postService.my(principalId, pageable).map(PostResponse::fromPost));
    }

    @PostMapping("/{postId}/likes")
    public Response<Void> postLike(@PathVariable(name = "postId") Long postId,
                                   @RequestHeader(name = "principalId") Long principalId) {
        postService.postLike(postId, principalId);
        return Response.success();
    }

    @GetMapping("/{postId}/likes")
    public Response<Integer> postLikeCount(@PathVariable(name = "postId") Long postId,
                                           @RequestHeader(name = "principalId") Long principalId) {
        return Response.success(postService.postLikeCount(postId, principalId));
    }

    @PostMapping("/{postId}/comments")
    public Response<Void> comment(@PathVariable(name = "postId") Long postId,
                                  @RequestBody PostCommentRequest request,
                                  @RequestHeader(name = "principalId") Long principalId) {
        postService.comment(postId, principalId, request.getComment());
        return Response.success();
    }

    @GetMapping("/{postId}/comments")
    public Response<Page<CommentResponse>> comment(@PathVariable(name = "postId") Long postId, Pageable pageable) {
        return Response.success(postService.getComment(postId, pageable).map(CommentResponse::fromComment));
    }

    @PostMapping("/{postId}/comments/{commentId}/likes")
    public Response<Void> commentLike(@PathVariable(name = "commentId") Long commentId,
                                      @RequestHeader(name = "principalId") Long principalId) {
        postService.commentLike(commentId, principalId);
        return Response.success();
    }

    @GetMapping("/{postId}/comments/{commentId}/likes")
    public Response<Integer> commentLikeCount(@PathVariable(name = "commentId") Long commentId) {
        return Response.success(postService.commentLikeCount(commentId));
    }
}
