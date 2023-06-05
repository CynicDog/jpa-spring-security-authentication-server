package practice.ch3demo2;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import practice.ch3demo2.config.SecurityConfig;
import practice.ch3demo2.config.SpringDataJpaConfig;
import practice.ch3demo2.controller.HelloController;
import practice.ch3demo2.entity.Authority;
import practice.ch3demo2.model.SecurityUser;
import practice.ch3demo2.entity.User;
import practice.ch3demo2.repository.UserRepository;
import practice.ch3demo2.service.JpaUserDetailsManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc @Import(HelloController.class)
@ContextConfiguration(classes = { SpringDataJpaConfig.class, SecurityConfig.class})
public class SecurityTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    public void databaseConnectionTest() {

        JpaUserDetailsManager jpaUserDetailsManager = new JpaUserDetailsManager(userRepository, passwordEncoder);

        SecurityUser userDetails = new SecurityUser(
                new User("john", "1234", List.of(
                        new Authority("john","read"),
                        new Authority("john","write")))
        );

        jpaUserDetailsManager.createUser(userDetails);

        SecurityUser found = (SecurityUser) jpaUserDetailsManager.loadUserByUsername(userDetails.getUsername());

        assertAll(
                () -> assertEquals("john", found.getUsername()),
                () -> assertTrue(passwordEncoder.matches("1234", found.getPassword())),
                () -> assertEquals(2, found.getAuthorities().size())
        );
    }

    @Test
    public void loggingWithAuthentication() throws Exception {

        JpaUserDetailsManager jpaUserDetailsManager = new JpaUserDetailsManager(userRepository, passwordEncoder);

        SecurityUser userDetails = new SecurityUser(
                new User("bill", "1234", List.of(
                        new Authority("bill","read"),
                        new Authority("bill","write")))
        );

        jpaUserDetailsManager.createUser(userDetails);

        mvc.perform(get("/hello")
                .with(httpBasic("bill", "1234")))
                .andExpect(status().isOk());
    }
}
