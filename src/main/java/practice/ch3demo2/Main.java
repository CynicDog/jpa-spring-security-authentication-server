package practice.ch3demo2;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import practice.ch3demo2.entity.Authority;
import practice.ch3demo2.model.SecurityUser;
import practice.ch3demo2.entity.User;
import practice.ch3demo2.repository.UserRepository;
import practice.ch3demo2.service.JpaUserDetailsManager;

import java.util.Collections;
import java.util.List;

@SpringBootApplication
public class Main {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

	@Bean
	public CommandLineRunner dataLoader(UserRepository userRepository, PasswordEncoder passwordEncoder) {

		JpaUserDetailsManager jpaUserDetailsManager = new JpaUserDetailsManager(userRepository, passwordEncoder);

		return new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception {
				SecurityUser user1 = new SecurityUser(new User("jane", "1234",
						List.of(new Authority("jane", "read"),
								new Authority("jane", "write"))));

				// no read authority
				SecurityUser user2 = new SecurityUser(new User("joe", "1234",
						Collections.emptyList()));

				// no read authority
				SecurityUser user3 = new SecurityUser(new User("john", "1234",
						List.of(new Authority("john", "write"))));

				jpaUserDetailsManager.createUser(user1);
				jpaUserDetailsManager.createUser(user2);
				jpaUserDetailsManager.createUser(user3);
			}
		};
	}
}
