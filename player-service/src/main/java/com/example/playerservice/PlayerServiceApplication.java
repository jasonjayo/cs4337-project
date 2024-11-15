package com.example.playerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.example.shared.feign")
public class PlayerServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(PlayerServiceApplication.class, args);
    }
}
