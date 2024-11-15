package com.example.api_gateway;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.FilterChainProxy;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = SecurityConfig.class)
class SecurityConfigTest {

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

//    @Test
//    void testSecurityFilterChainConfigured() {
//        assertNotNull(springSecurityFilterChain, "Spring Security Filter Chain should be configured");
//    }
}
