package org.example.gateway.config;

import org.example.gateway.service.OAuth2LoginSuccessHandler;
import org.springframework.cloud.gateway.filter.headers.ForwardedHeadersFilter;
import org.springframework.cloud.gateway.filter.headers.XForwardedHeadersFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.server.adapter.ForwardedHeaderTransformer;

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
                        .pathMatchers("/api/login/**", "/api/oauth2/**").permitAll()
                        .pathMatchers("/login/**", "/oauth2/**").permitAll()
                        .pathMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .pathMatchers("/actuator/**", "/health").permitAll()
                        .anyExchange().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .authenticationSuccessHandler(successHandler)
                );

        return http.build();
    }

//    @Bean
//    public ForwardedHeaderTransformer forwardedHeaderTransformer() {
//        ForwardedHeaderTransformer transformer = new ForwardedHeaderTransformer();
//        transformer.setRemoveOnly(false);
//        return transformer;
//    }

    @Bean
    public ForwardedHeadersFilter forwardedHeadersFilter() {
        return new ForwardedHeadersFilter();
    }
//
//    @Bean
//    public XForwardedHeadersFilter xForwardedHeadersFilter() {
//        return new XForwardedHeadersFilter();
//    }
}