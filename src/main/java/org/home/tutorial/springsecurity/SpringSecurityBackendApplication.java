package org.home.tutorial.springsecurity;

import org.home.tutorial.springsecurity.model.User;
import org.home.tutorial.springsecurity.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SpringSecurityBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityBackendApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(UserRepository users, PasswordEncoder encoder) {
		return args -> {
			users.save(new User("user", encoder.encode("user"), "ROLE_USER"));
			users.save(new User("admin", encoder.encode("admin"), "ROLE_USER,ROLE_ADMIN"));
		};
	}

}
