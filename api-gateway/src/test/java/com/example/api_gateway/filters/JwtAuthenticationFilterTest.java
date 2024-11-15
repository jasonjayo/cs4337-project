package com.example.api_gateway.filters;

import com.example.api_gateway.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class JwtAuthenticationFilterTest {

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private WebFilterChain webFilterChain;

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    void testFilter_withValidToken() {
//        String validToken = Jwts.builder()
//                .setSubject("testUser")
//                .signWith(Keys.hmacShaKeyFor("bf83d510d69b7a6db12aa6c4f2d51ad29945fcc70995041dc5affd897b9e33fc".getBytes()), SignatureAlgorithm.HS256)
//                .compact();
//
//        ServerWebExchange exchange = MockServerWebExchange.from(MockServerHttpRequest.get("/")
//                .header(HttpHeaders.AUTHORIZATION, "Bearer " + validToken)
//                .build());
//
//        Claims claims = mock(Claims.class);
//        when(claims.getSubject()).thenReturn("testUser");
//        when(jwtUtils.parseToken(validToken)).thenReturn(claims);
//        when(webFilterChain.filter(exchange)).thenReturn(Mono.empty());
//
//        Mono<Void> result = jwtAuthenticationFilter.filter(exchange, webFilterChain);
//
//        StepVerifier.create(result)
//                .verifyComplete();
//
//        Mono<SecurityContext> context = ReactiveSecurityContextHolder.getContext();
//        StepVerifier.create(context)
//                .expectNextMatches(securityContext ->
//                        securityContext.getAuthentication() != null &&
//                                securityContext.getAuthentication().getName().equals("testUser"))
//                .verifyComplete();
//    }
//
//    @Test
//    void testFilter_withInvalidToken() {
//        // Mock an invalid token
//        String invalidToken = "invalid.jwt.token";
//        ServerWebExchange exchange = MockServerWebExchange.from(MockServerHttpRequest.get("/")
//                .header(HttpHeaders.AUTHORIZATION, "Bearer " + invalidToken)
//                .build());
//
//        when(jwtUtils.parseToken(invalidToken)).thenThrow(new io.jsonwebtoken.JwtException("Invalid token"));
//
//        Mono<Void> result = jwtAuthenticationFilter.filter(exchange, webFilterChain);
//
//        StepVerifier.create(result)
//                .expectErrorMatches(throwable -> throwable instanceof io.jsonwebtoken.JwtException &&
//                        throwable.getMessage().equals("Invalid token"))
//                .verify();
//    }
//
//    @Test
//    void testFilter_noToken() {
//        // No Authorization header
//        ServerWebExchange exchange = MockServerWebExchange.from(MockServerHttpRequest.get("/").build());
//
//        when(webFilterChain.filter(exchange)).thenReturn(Mono.empty());
//
//        Mono<Void> result = jwtAuthenticationFilter.filter(exchange, webFilterChain);
//
//        StepVerifier.create(result)
//                .verifyComplete();
//
//        Mono<SecurityContext> context = ReactiveSecurityContextHolder.getContext();
//        StepVerifier.create(context)
//                .expectNextMatches(securityContext -> securityContext.getAuthentication() == null)
//                .verifyComplete();
//    }
}
