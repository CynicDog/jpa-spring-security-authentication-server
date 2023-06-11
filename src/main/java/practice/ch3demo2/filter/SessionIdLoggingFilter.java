package practice.ch3demo2.filter;

import org.jboss.logging.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SessionIdLoggingFilter extends OncePerRequestFilter {

    private final Logger logger = Logger.getLogger(SessionIdLoggingFilter.class.getName());

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String sessionId = request.getSession().getId();

        logger.info("Processing request(s) of a session (" + sessionId +")");

        filterChain.doFilter(request, response);
    }
}
