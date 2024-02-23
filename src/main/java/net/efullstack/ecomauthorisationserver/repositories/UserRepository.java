package net.efullstack.ecomauthorisationserver.repositories;

import net.efullstack.ecomauthorisationserver.models.User;
import org.springframework.data.repository.ListCrudRepository;

public interface UserRepository extends ListCrudRepository<User, Integer> {
    public User findCustomerByUsername(String userName);
}
