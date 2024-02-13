package com.bokyoung.userService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class UserServiceAppliction {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceAppliction.class, args);
    }
}
