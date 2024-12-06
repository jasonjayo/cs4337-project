package com.example.api_gateway.filters;

import com.example.api_gateway.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter implements WebFilter {

    /**
     * Validate the integrity of JWT token in the Authorization header
     *
     * @param exchange essentially the current HTTP request we're processing
     * @param chain    the chain of filters we'll apply to the request
     * @return signal of operation completing or failing
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();

        // bypass JWT validation for public endpoints
        if (path.startsWith("/login") || path.startsWith("/oauth2")) {
            return chain.filter(exchange);
        }

        // get Authorization header
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        // check for missing auth header and invalid value
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // get token, substring to remove Bearer at start
            try {
                Claims claims = JwtUtils.parseToken(token);
                String username = claims.getSubject();

                // create authentication object to represent authenticated user
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(username, null, null);
                // create new security context
                // this security context represents the security info for this request, hence why it uses
                // the auth object from above.
                SecurityContext securityContext = new SecurityContextImpl(authentication);

                // Set the SecurityContext in the reactive context
                return chain.filter(exchange)
                        // write to context to ensure rest of chain for the request sees user as authenticated
                        .contextWrite(ReactiveSecurityContextHolder.withSecurityContext(Mono.just(securityContext)));

            } catch (Exception e) {
                // return 401
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
        } else {
            // no token / invalid format -> return 401
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
    }
}
