package com.bokyoung.activityService.resilience_test;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "Resilience-newsFeed-service", url = "${feign.url.prefix}")
public interface ErrorfulFeignClient {

    @GetMapping("/errorful/case1")
    ResponseEntity<String> case1();

    @GetMapping("/errorful/case2")
    ResponseEntity<String> case2();

    @GetMapping("/errorful/case3")
    ResponseEntity<String> case3();

}
