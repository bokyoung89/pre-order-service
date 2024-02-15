package com.bokyoung.activityService.controller;

import com.bokyoung.activityService.controller.response.Response;
import com.bokyoung.activityService.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/follow")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    // TODO : 추후 api-gateway 인증 필터 구현해 인증된 사용자 정보 받을 것 (Long principalId)
    @PostMapping("/{followeeId}")
    public Response<Void> addFollow(@PathVariable(name="followeeId") Long followeeId, Long principalId){
        followService.addFollow(followeeId, principalId);
        return Response.success();
    }

    // TODO : 추후 api-gateway 인증 필터 구현해 인증된 사용자 정보 받을 것 (Long principalId)
    @DeleteMapping("/{followeeId}")
    public Response<Void> deleteFollow(@PathVariable(name="followeeId") Long followeeId, Long principalId){
        followService.addFollow(followeeId, principalId);
        return Response.success();
    }
}
