package com.bokyoung.activityService.application.controller;

import com.bokyoung.activityService.dto.response.Response;
import com.bokyoung.activityService.application.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/activity-service/follow")
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
