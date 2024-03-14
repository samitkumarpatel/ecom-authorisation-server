package net.efullstack.ecomauthorisationserver;

import lombok.RequiredArgsConstructor;
import net.efullstack.ecomauthorisationserver.models.User;
import net.efullstack.ecomauthorisationserver.repositories.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class EcomAuthorisationServerApplication {
	final UserRepository userRepository;
	final PasswordEncoder passwordEncoder;
	public static void main(String[] args) {
		SpringApplication.run(EcomAuthorisationServerApplication.class, args);
	}

	@EventListener(ApplicationStartedEvent.class)
	void applicationStarterEvent() {
		userRepository
				.saveAll(
						List.of(
								new User(null, "one", passwordEncoder.encode("secret1")),
								new User(null, "two", passwordEncoder.encode("secret2"))
						)
				);
	}

}
