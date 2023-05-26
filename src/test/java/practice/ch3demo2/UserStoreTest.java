package practice.ch3demo2;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import practice.ch3demo2.config.SpringDataJpaConfig;
import practice.ch3demo2.model.Authority;
import practice.ch3demo2.model.SecurityUser;
import practice.ch3demo2.model.User;
import practice.ch3demo2.repository.UserRepository;
import practice.ch3demo2.service.JpaUserDetailsManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringDataJpaConfig.class})
@SpringBootTest
public class UserStoreTest {

    @Autowired
    UserRepository userRepository;

    @Test
    public void databaseConnectionTest() {

        JpaUserDetailsManager jpaUserDetailsManager = new JpaUserDetailsManager(userRepository);

        SecurityUser userDetails = new SecurityUser(
                new User("john", "1234", List.of(
                        new Authority("john","read"),
                        new Authority("john","write")))
        );

        jpaUserDetailsManager.createUser(userDetails);

        SecurityUser found = (SecurityUser) jpaUserDetailsManager.loadUserByUsername(userDetails.getUsername());

        assertAll(
                () -> assertEquals("john", found.getUsername()),
                () -> assertEquals("1234", found.getPassword()),
                () -> assertEquals(2, found.getAuthorities().size())
        );
    }
}
