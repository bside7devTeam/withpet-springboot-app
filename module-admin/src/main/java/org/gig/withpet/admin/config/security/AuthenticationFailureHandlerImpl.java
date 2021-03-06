package org.gig.withpet.admin.config.security;

import lombok.RequiredArgsConstructor;
import org.gig.withpet.core.domain.user.administrator.AdministratorService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author : JAKE
 * @date : 2022/05/24
 */
@Component
@RequiredArgsConstructor
public class AuthenticationFailureHandlerImpl implements AuthenticationFailureHandler {

    private static final String DEFAULT_URL = "/login?error=500";
    private final AdministratorService administratorService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String errorMessage = null;
        if (exception instanceof BadCredentialsException || exception instanceof InternalAuthenticationServiceException) {
            errorMessage = "계정 정보가 잘못되었습니다.";
            administratorService.increasePasswordFailureCount(request.getParameter("username"));
        } else  if (exception instanceof LockedException) {
            errorMessage = "패스워드 5회 이상 틀려 계정이 잠겼습니다.";
        } else {
            errorMessage = "알 수 없는 문제가 발생하였습니다.";
        }


        request.setAttribute("authError", errorMessage);
        request.getRequestDispatcher(DEFAULT_URL).forward(request, response);
    }

}
