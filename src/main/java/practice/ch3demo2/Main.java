package practice.ch3demo2;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import practice.ch3demo2.model.Authority;
import practice.ch3demo2.model.SecurityUser;
import practice.ch3demo2.model.User;
import practice.ch3demo2.repository.UserRepository;
import practice.ch3demo2.service.JpaUserDetailsManager;

import java.util.List;

@SpringBootApplication
public class Main {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

	@Bean
	public CommandLineRunner dataLoader(UserRepository userRepository) {

		JpaUserDetailsManager jpaUserDetailsManager = new JpaUserDetailsManager(userRepository);

		return new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception {
				SecurityUser user = new SecurityUser(new User("jane", "1234",
										List.of(new Authority("jane", "read"),
												new Authority("jane", "write"))));

				jpaUserDetailsManager.createUser(user);
			}
		};
	}
}
