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
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

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
	//https://docs.spring.io/spring-authorization-server/reference/getting-started.html
	public SecurityFilterChain securityFilterChain(HttpSecurity http) {
		OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);

		http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
					.oidc(Customizer.withDefaults());	// Enable OpenID Connect 1.0

		// Redirect to the login page when not authenticated from the
		// authorization endpoint
		http.exceptionHandling((exceptions) -> exceptions
				.defaultAuthenticationEntryPointFor(
						new LoginUrlAuthenticationEntryPoint("/login"),
						new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
				)
		)
		// Accept access tokens for User Info and/or Client Registration
		.oauth2ResourceServer((resourceServer) -> resourceServer
				.jwt(Customizer.withDefaults()));
		return http.cors(Customizer.withDefaults()).build();
	}

	@Bean
	@Order(2)
	@SneakyThrows
	public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) {
		http
				.authorizeHttpRequests((authorize) -> authorize
						//.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/register")).permitAll()
						.requestMatchers("/error","/signup", "/register").permitAll()
						.anyRequest().authenticated()
				)
				.csrf((csrf) -> csrf
						.ignoringRequestMatchers("/register")
				)
				.formLogin(Customizer.withDefaults());
		return http.cors(Customizer.withDefaults()).build();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		config.addAllowedOrigin("*");
		config.setAllowCredentials(true);
		source.registerCorsConfiguration("/**", config);
		return source;
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
