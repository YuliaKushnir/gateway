package org.example.gateway.controller;

//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.server.WebSession;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ProfileController {

//    @GetMapping("/api/profile")
//    public Map<String, Object> profile(@AuthenticationPrincipal OAuth2User oAuth2User, WebSession session) {
//        Map<String, Object> profile = new HashMap<>(oAuth2User.getAttributes());
//        profile.put("userId", session.getAttribute("userId"));
//        return profile;
//    }

    @GetMapping("/api/profile")
    public Map<String, Object> profile() {
        Map<String, Object> profile = new HashMap<>();
        profile.put("userId", 1);
        profile.put("name", "Marusia");
        profile.put("email", "marusia@example.com");
        return profile;
    }
}
