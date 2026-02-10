package org.example.gateway.config;

import org.example.gateway.service.OAuth2LoginSuccessHandler;
//import org.springframework.cloud.gateway.filter.headers.ForwardedHeadersFilter;
//import org.springframework.cloud.gateway.filter.headers.XForwardedHeadersFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
//import org.springframework.web.server.adapter.ForwardedHeaderTransformer;

@EnableWebFluxSecurity
@Configuration
public class SecurityConfig {

    private final OAuth2LoginSuccessHandler successHandler;

    public SecurityConfig(OAuth2LoginSuccessHandler successHandler) {
        this.successHandler = successHandler;
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchange -> exchange
//                        .pathMatchers(
//                                "/api/login/oauth2/**",
//                                "/login/oauth2/**",
//                                "/oauth2/**",
//                                "/api/user/**",
//                                "/api/post/**")
//                        .permitAll()
//                        .pathMatchers(HttpMethod.OPTIONS, "/**").permitAll()
//                        .pathMatchers("/actuator/**", "/health").permitAll()
//                        .anyExchange().authenticated()
                        .anyExchange().permitAll()
                );
//                .oauth2Login(oauth2 -> oauth2
//                        .authenticationSuccessHandler(successHandler)
//                );

        return http.build();
    }
}