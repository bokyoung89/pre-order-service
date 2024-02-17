package com.bokyoung.activityService.resilience_test;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FeignClientController {

    private final ErrorfulFeignClient errorfulFeignClient;


    @GetMapping("/errorful/case1")
    public ResponseEntity<String> case1() {
        return errorfulFeignClient.case1();
    }

    @GetMapping("/errorful/case2")
    public ResponseEntity<String> case2() {
        return errorfulFeignClient.case2();
    }

    @GetMapping("/errorful/case3")
    public ResponseEntity<String> case3() {
        return errorfulFeignClient.case3();
    }
}
