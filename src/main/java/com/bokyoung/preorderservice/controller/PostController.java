package com.bokyoung.preorderservice.controller;

import com.bokyoung.preorderservice.controller.request.PostCreateReqeust;
import com.bokyoung.preorderservice.controller.response.Response;
import com.bokyoung.preorderservice.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
