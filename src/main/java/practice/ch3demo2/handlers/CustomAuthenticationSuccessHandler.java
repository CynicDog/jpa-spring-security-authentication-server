package practice.ch3demo2.handlers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collection;

@Component
public class CustomAuthenticationSuccessHandler implements org.springframework.security.web.authentication.AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        var auth = authorities.stream()
                .filter(grantedAuthority -> "read".equals(grantedAuthority.getAuthority()))
                .findFirst();

        if (auth.isPresent()) {
            httpServletResponse.sendRedirect("/hello");
        } else {
            httpServletResponse.sendRedirect("/no-read-auth");
        }
    }
}
