package org.gig.withpet.api.controller.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.gig.withpet.api.config.jwt.JwtTokenProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "HealthCheck API V1")
@RequiredArgsConstructor
@RestController
public class HealthCheckController {
    private final JwtTokenProvider jwtTokenProvider;

    @ApiOperation(value = "서버상태 체크 API")
    @GetMapping("/health-check")
    public String healthCheck() {
        return "Status is OK";
    }

    @ApiOperation(value = "Token Valid 체크 API")
    @GetMapping("/token-check")
    public String tokenCheck() {
        return "Token is Valid";
    }
}
