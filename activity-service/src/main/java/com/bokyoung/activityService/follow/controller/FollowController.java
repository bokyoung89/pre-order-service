package com.bokyoung.activityService.follow.controller;

import com.bokyoung.activityService.common.response.Response;
import com.bokyoung.activityService.follow.application.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/follow")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping("/{followeeId}")
    public Response<Void> addFollow(@PathVariable(name="followeeId") Long followeeId, @RequestHeader(name="principalId") Long principalId) {
        followService.addFollow(followeeId, principalId);
        return Response.success();
    }

    @DeleteMapping("/{followeeId}")
    public Response<Void> cancelFollow(@PathVariable(name="followeeId") Long followeeId, @RequestHeader(name="principalId") Long principalId) {
        followService.cancelFollow(followeeId, principalId);
        return Response.success();
    }
}
