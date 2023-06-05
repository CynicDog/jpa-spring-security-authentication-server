package practice.ch3demo2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import practice.ch3demo2.handler.CustomAuthenticationFailureHandler;
import practice.ch3demo2.handler.CustomAuthenticationSuccessHandler;
import practice.ch3demo2.repository.TokenRepository;
import practice.ch3demo2.repository.UserRepository;
import practice.ch3demo2.service.CsrfTokenService;
import practice.ch3demo2.service.JpaUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf(csrfConfigurer -> {
            csrfConfigurer.csrfTokenRepository(csrfTokenRepository());
        });

        http.formLogin(
                formLoginConfigurer -> {
                    formLoginConfigurer.failureHandler(authenticationFailureHandler());
                    formLoginConfigurer.successHandler(authenticationSuccessHandler());
                }
        ).httpBasic();

        http.authorizeRequests()
                .anyRequest()
                .authenticated();
    }

    @Bean
    public CsrfTokenRepository csrfTokenRepository() {
        return new CsrfTokenService(tokenRepository);
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new JpaUserDetailsManager(userRepository, passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(8);
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }
}

