package org.example.gateway.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class OAuth2LoginSuccessHandler implements ServerAuthenticationSuccessHandler {

    private final WebClient webClient;

    private final RedirectServerAuthenticationSuccessHandler redirectHandler =
            new RedirectServerAuthenticationSuccessHandler("https://34.118.68.15.nip.io");

    public OAuth2LoginSuccessHandler(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Mono<Void> onAuthenticationSuccess(
            WebFilterExchange webFilterExchange,
            Authentication authentication
    ) {

        OidcUser oidcUser = (OidcUser) authentication.getPrincipal();

        return webClient.post()
                .uri("http://posts-service:8080/api/internal/user/oauth")
                .bodyValue(Map.of(
                        "email", oidcUser.getEmail(),
                        "name", oidcUser.getFullName()
                ))
                .retrieve()
                .bodyToMono(Long.class)
                .flatMap(userId ->
                        webFilterExchange.getExchange()
                                .getSession()
                                .doOnNext(session ->
                                        session.getAttributes().put("userId", userId)
                                )
                                .then()
                )
                .then(redirectHandler.onAuthenticationSuccess(webFilterExchange, authentication));
    }
}
