package com.example.api_gateway.filters;

import com.example.api_gateway.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.http.server.reactive.MockServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class JwtAuthenticationFilterTest {

    private static final String TEST_SECRET_KEY = "bf83d510d69b7a6db12aa6c4f2d51ad29945fcc70995041dc5affd897b9e33fc";

    @Test
    void testFilter_ValidToken() {
        // Arrange
        String token = Jwts.builder()
                .setSubject("testuser")
                .signWith(Keys.hmacShaKeyFor(TEST_SECRET_KEY.getBytes()))
                .compact();

        MockServerHttpRequest request = MockServerHttpRequest.get("/api/test")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .build();
        ServerWebExchange exchange = mock(ServerWebExchange.class);
        when(exchange.getRequest()).thenReturn(request);

        WebFilterChain chain = mock(WebFilterChain.class);
        when(chain.filter(exchange)).thenReturn(Mono.empty());

        try (MockedStatic<JwtUtils> jwtUtilsMock = mockStatic(JwtUtils.class)) {
            Claims claims = mock(Claims.class);
            when(claims.getSubject()).thenReturn("testuser");
            jwtUtilsMock.when(() -> JwtUtils.parseToken(token)).thenReturn(claims);

            JwtAuthenticationFilter filter = new JwtAuthenticationFilter();

            // Act
            Mono<Void> result = filter.filter(exchange, chain);

            // Assert
            StepVerifier.create(result).verifyComplete();
            verify(chain, times(1)).filter(exchange);
        }
    }

    @Test
    void testFilter_InvalidToken() {
        // Arrange
        String invalidToken = "invalid.token";

        MockServerHttpRequest request = MockServerHttpRequest.get("/api/test")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + invalidToken)
                .build();
        ServerWebExchange exchange = mock(ServerWebExchange.class);
        when(exchange.getRequest()).thenReturn(request);

        MockServerHttpResponse response = new MockServerHttpResponse();
        when(exchange.getResponse()).thenReturn(response);

        WebFilterChain chain = mock(WebFilterChain.class);

        JwtAuthenticationFilter filter = new JwtAuthenticationFilter();

        // Act
        Mono<Void> result = filter.filter(exchange, chain);

        // Assert
        StepVerifier.create(result)
                .expectComplete()
                .verify();
        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatusCode().value());
        verify(chain, never()).filter(exchange);
    }
}
