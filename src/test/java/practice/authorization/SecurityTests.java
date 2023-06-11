package practice.authorization;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import practice.authorization.config.SecurityConfig;
import practice.authorization.config.SpringDataJpaConfig;
import practice.authorization.controller.AuthController;
import practice.authorization.repository.OtpRepository;
import practice.authorization.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc @Import(AuthController.class)
@ContextConfiguration(classes = { SpringDataJpaConfig.class, SecurityConfig.class})
public class SecurityTests {

    @Autowired
    private MockMvc mvc;

    @Mock
    private HttpServletRequest httpServletRequest;

    @Mock
    private HttpServletResponse httpServletResponse;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    OtpRepository otpRepository;

//    @Mock
//    TokenRepository tokenRepository;

    @Test
    public void databaseConnectionTest() {
//
//        JpaUserDetailsManager jpaUserDetailsManager = new JpaUserDetailsManager(userRepository, passwordEncoder, otpRepository);
//
//        SecurityUser userDetails = new SecurityUser(
//                new User("john", "1234", List.of(
//                        new Authority("john","read"),
//                        new Authority("john","write")))
//        );
//
//        jpaUserDetailsManager.createUser(userDetails);
//
//        SecurityUser found = (SecurityUser) jpaUserDetailsManager.loadUserByUsername(userDetails.getUsername());
//
//        assertAll(
//                () -> assertEquals("john", found.getUsername()),
//                () -> assertTrue(passwordEncoder.matches("1234", found.getPassword())),
//                () -> assertEquals(2, found.getAuthorities().size())
//        );
    }

    @Test
    public void loggingWithAuthenticationTest() throws Exception {
//
//        JpaUserDetailsManager jpaUserDetailsManager = new JpaUserDetailsManager(userRepository, passwordEncoder, otpRepository);
//
//        SecurityUser userDetails = new SecurityUser(
//                new User("bill", "1234", List.of(
//                        new Authority("bill","read"),
//                        new Authority("bill","write")))
//        );
//
//        jpaUserDetailsManager.createUser(userDetails);
//
//        mvc.perform(formLogin("/login").user("bill").password("1234"))
//                .andExpect(authenticated());
    }

    @Test
    public void csrfServiceTest() {

//        CsrfTokenService csrfTokenService = new CsrfTokenService(tokenRepository);
//
//        String sessionId = "sessionId for testing";
//
//        Token token = new Token();
//        token.setToken("token for testing");
//        token.setSessionId(sessionId);
//        token.setUsername("username for testing");
//
//        when(httpServletRequest.getSession()).thenReturn(mock(HttpSession.class));
//        when(httpServletRequest.getSession().getId()).thenReturn(sessionId);
//        when(tokenRepository.findTokenBySessionId(sessionId)).thenReturn(Optional.of(token));
//
//        CsrfToken csrfToken = csrfTokenService.loadToken(httpServletRequest);
//
//        assertEquals("X-CSRF-TOKEN", csrfToken.getHeaderName());
//        assertEquals("_csrf", csrfToken.getParameterName());
//        assertEquals("token for testing", csrfToken.getToken());
    }
}
