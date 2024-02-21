package net.efullstack.ecomauthorisationserver;

import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RequestMatcher;

@SpringBootApplication
@EnableWebSecurity
public class EcomAuthorisationServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcomAuthorisationServerApplication.class, args);
	}

	/*@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}*/

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
						.requestMatchers(HttpMethod.GET,"/register").permitAll()
						.requestMatchers(HttpMethod.POST, "/register").permitAll()
						.anyRequest().authenticated()
				)
				// Form login handles the redirect to the login page from the
				// authorization server filter chain
				.formLogin(Customizer.withDefaults());


		return http.build();
	}
}
