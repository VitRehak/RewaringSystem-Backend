package cz.morosystem.RewardingSystem.configuration.security;

import cz.morosystem.RewardingSystem.service.EmployeeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SuccessLoginHandler implements AuthenticationSuccessHandler {

    final
    EmployeeService employeeService;

    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    public SuccessLoginHandler(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2AuthenticatedPrincipal oAuth2User = (OAuth2AuthenticatedPrincipal) authentication.getPrincipal();
        employeeService.loginEmployee(oAuth2User);
        redirectStrategy.sendRedirect(request, response, "/");
    }
}
