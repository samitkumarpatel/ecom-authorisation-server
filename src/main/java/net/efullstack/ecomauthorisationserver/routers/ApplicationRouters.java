package net.efullstack.ecomauthorisationserver.routers;

import lombok.RequiredArgsConstructor;
import net.efullstack.ecomauthorisationserver.models.User;
import net.efullstack.ecomauthorisationserver.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ApplicationRouters {
    final UserRepository userRepository;
    final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {

        return ResponseEntity.ok(userRepository.save(
                new User(null, user.username(), passwordEncoder.encode(user.password()))
        ));
    }
}
