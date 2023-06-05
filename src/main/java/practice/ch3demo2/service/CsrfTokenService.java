package practice.ch3demo2.service;

import org.jboss.logging.Logger;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.stereotype.Component;
import practice.ch3demo2.entity.Token;
import practice.ch3demo2.repository.TokenRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.UUID;

@Component
public class CsrfTokenService implements CsrfTokenRepository {

    private TokenRepository tokenRepository;

    private Logger logger = Logger.getLogger(CsrfTokenService.class.getName());

    public CsrfTokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public CsrfToken generateToken(HttpServletRequest httpServletRequest) {

        String uuid = UUID.randomUUID().toString();

        logger.info("A CSRF token for a session generated.");

        return new DefaultCsrfToken("X-CSRF-TOKEN", "_csrf", uuid);
    }

    @Override
    public void saveToken(CsrfToken csrfToken, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        String sessionId = httpServletRequest.getSession().getId();

        Optional<Token> existingToken = tokenRepository.findTokenBySessionId(sessionId);

        if (existingToken.isPresent()) {
            Token token = existingToken.get();
            token.setToken(csrfToken.getToken());
        } else {
            Token token = new Token();

            token.setToken(csrfToken.getToken());
            token.setSessionId(sessionId);

            logger.info("Saving a CSRF token for a session.");
            tokenRepository.save(token);
        }
    }

    @Override
    public CsrfToken loadToken(HttpServletRequest httpServletRequest) {

        String sessionId = httpServletRequest.getSession().getId();

        logger.info("Load a CSRF token for a session.");
        Optional<Token> existingToken = tokenRepository.findTokenBySessionId(sessionId);

        if (existingToken.isPresent()) {
            Token token = existingToken.get();

            return new DefaultCsrfToken("X-CSRF-TOKEN", "_csrf", token.getToken());
        }

        return null;
    }
}
