package com.bokyoung.apiGatewayService.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {
    @Value("${jwt.secret-key}")
    private String key;
    Environment env;

    public AuthorizationHeaderFilter(Environment env) {
        super(Config.class);
        this.env = env;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            //check token is null
            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return onError(exchange, "Error occurs while getting header. header is null", HttpStatus.UNAUTHORIZED);
            }

            String authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            String token = authorizationHeader.split(" ")[1].trim();
            String userId = JwtTokenUtils.getUserId(token, key);

            //check token is expired
            if(JwtTokenUtils.isExpired(token, key)) {
                return onError(exchange, "Key is expired", HttpStatus.UNAUTHORIZED);
            }

            request = request.mutate()
                    .header("principalId", userId)
                    .build();

            log.info("RequestHeader: {}", request.getHeaders());

            return chain.filter(exchange.mutate()
                    .request(request)
                    .build());
        });
    }

    //Mono, Flux -> Strping WebFlux : 클라이언트 요청이 들어왔을 때 반환해주는 데이터 타입. 단일 값이면 Mono, 다중값이면 Flux 사용
    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);

        log.error(err);
        return response.setComplete();
    }

    public static class Config {
    }
}
