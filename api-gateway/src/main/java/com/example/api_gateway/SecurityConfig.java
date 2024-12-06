package com.example.api_gateway;

import com.example.api_gateway.filters.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;

import java.util.Arrays;
import java.util.List;

/*
    Set up security configuration for API gateway
*/

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {

    // we'll use this to validate integrity of JWT tokens
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    /**
     * sets up a filter to ensure requests are authenticated
     *
     * @param http configure application security
     * @return chain of filters and configurations for CSRF, CORS etc.
     */
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // enable CORS
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchange -> exchange
                        // requests to public or login endpoints should be allowed without being authenticated
                        // without these exceptions, it'd be impossible  to become authenticated
                        .pathMatchers("/login/**", "/public/**", "/auth/**", "/oauth2/**").permitAll()
                        .anyExchange().authenticated() // all other routes require authentication
                )
                .addFilterAt(jwtAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION) // validate JWT token
                .build();
    }

    /**
     * set up our CORS configuration
     *
     * @return CORS configuration for use above
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        // set up our cross-origin resource sharing config
        // this is particularly important because our frontend runs on a different port
        // than our API gateway.
        // if we don't set this up properly, browsers will block our requests
        CorsConfiguration configuration = new CorsConfiguration();
        // allow requests from these 2 origins (first for dev, second is our live domain)
        configuration.setAllowedOrigins(List.of("http://127.0.0.1:5173", "http://findyourspark.ie:5173"));
        // allow all  the common HTTP methods
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        // we need to make sure important headers like Authorization are allowed because the client's JWT token
        // will be stored in it
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
