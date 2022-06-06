package org.gig.withpet.api.controller.v1;

import lombok.RequiredArgsConstructor;
import org.gig.withpet.api.config.jwt.JwtTokenProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class HealthCheckController {
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/health-check")
    public String healthCheck() {
        return "Status is OK";
    }

    @GetMapping("/token-check")
    public String tokenCheck() {
        return "Token is Valid";
    }
}
