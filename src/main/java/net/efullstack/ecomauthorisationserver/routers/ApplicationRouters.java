package net.efullstack.ecomauthorisationserver.routers;

import net.efullstack.ecomauthorisationserver.models.Customer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ApplicationRouters {

    @GetMapping("/register")
    public ResponseEntity<Map> getRegister() {
        return ResponseEntity.ok(Map.of("message","success"));
    }
    @PostMapping("/register")
    public ResponseEntity<Customer> register(@RequestBody Customer customer) {
        return ResponseEntity.ok(customer);
    }
}
