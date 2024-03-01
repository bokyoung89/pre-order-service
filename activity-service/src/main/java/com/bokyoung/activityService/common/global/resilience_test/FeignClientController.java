package com.bokyoung.activityService.common.global.resilience_test;

import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FeignClientController {

    private final ErrorfulFeignClient errorfulFeignClient;
    CircuitBreakerFactory circuitBreakerFactory;
    RetryRegistry retryRegistry;

    @GetMapping("/errorful/case1")
    public ResponseEntity<String> case1() {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");
        Retry retry = retryRegistry.retry("retry");
        String response = circuitBreaker.run(
                () -> Retry.decorateFunction(retry, s -> errorfulFeignClient.case1().getBody()).apply(1), throwable ->
                        "Fallback response");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/errorful/case2")
    public ResponseEntity<String> case2() {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");
        Retry retry = retryRegistry.retry("retry");
        String response = circuitBreaker.run(
                () -> Retry.decorateFunction(retry, s -> errorfulFeignClient.case2().getBody()).apply(1), throwable ->
                        "Fallback response");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/errorful/case3")
    public ResponseEntity<String> case3() {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");
        Retry retry = retryRegistry.retry("retry");
        String response = circuitBreaker.run(
                () -> Retry.decorateFunction(retry, s -> errorfulFeignClient.case3().getBody()).apply(1), throwable ->
                        "Fallback response");

        return ResponseEntity.ok(response);
    }
}
