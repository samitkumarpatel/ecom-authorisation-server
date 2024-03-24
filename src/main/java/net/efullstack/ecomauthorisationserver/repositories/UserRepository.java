package net.efullstack.ecomauthorisationserver.repositories;

import net.efullstack.ecomauthorisationserver.models.User;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends ListCrudRepository<User, Integer> {
    public UserDetails findCustomerByUsername(String userName);
}
