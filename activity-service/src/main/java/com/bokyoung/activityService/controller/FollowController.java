package com.bokyoung.activityService.controller;

import com.bokyoung.activityService.controller.response.Response;
import com.bokyoung.activityService.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/follow")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping("/{followeeId}")
    public Response<Void> addFollow(@PathVariable(name="followeeId") Long followeeId, Authentication authentication){
        followService.addFollow(followeeId, authentication.getName());
        return Response.success();
    }

    //TODO : implement
    @DeleteMapping("/{followeeId}")
    public Response<Void> deleteFollow(@PathVariable(name="followeeId") Long followeeId, Authentication authentication){
        followService.addFollow(followeeId, authentication.getName());
        return Response.success();
    }
}