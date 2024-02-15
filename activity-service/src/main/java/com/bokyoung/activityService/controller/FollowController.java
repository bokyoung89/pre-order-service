package com.bokyoung.activityService.controller;

import com.bokyoung.activityService.controller.response.Response;
import com.bokyoung.activityService.service.FollowService;
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
