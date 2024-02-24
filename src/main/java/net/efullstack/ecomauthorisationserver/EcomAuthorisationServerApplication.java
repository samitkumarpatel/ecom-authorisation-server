package net.efullstack.ecomauthorisationserver;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.efullstack.ecomauthorisationserver.models.User;
import net.efullstack.ecomauthorisationserver.repositories.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.List;

@SpringBootApplication
@EnableWebSecurity
@RequiredArgsConstructor
public class EcomAuthorisationServerApplication {
	final UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(EcomAuthorisationServerApplication.class, args);
	}

	@EventListener(ApplicationStartedEvent.class)
	void applicationStarterEvent() {
		userRepository
				.saveAll(
						List.of(
								new User(null, "one", passwordEncoder().encode("secret1")),
								new User(null, "two", passwordEncoder().encode("secret2"))
						)
				);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Order(1)
	@SneakyThrows
	//https://docs.spring.io/spring-security/reference/servlet/authorization/authorize-http-requests.html
	public SecurityFilterChain securityFilterChain(HttpSecurity http) {
		OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
		return http.build();
	}

	@Bean
	@Order(2)
	@SneakyThrows
	public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) {
		http
				.authorizeHttpRequests((authorize) -> authorize
						//.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/register")).permitAll()
						.requestMatchers("/error","/signup","/logout", "/register").permitAll()
						.anyRequest().authenticated()
				)
				.csrf((csrf) -> csrf
						.ignoringRequestMatchers("/register")
				)
				.formLogin(Customizer.withDefaults());
//				.formLogin(f -> f.loginPage("/login").permitAll())
//				.logout(Customizer.withDefaults());
		return http.build();
	}

	/*@Bean
	//https://docs.spring.io/spring-security/reference/5.8/migration/servlet/config.html
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web
				.ignoring()
				.requestMatchers("/error", "/signup")
				.requestMatchers(HttpMethod.POST, "/register");
	}*/
}
