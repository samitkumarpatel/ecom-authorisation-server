package net.efullstack.ecomauthorisationserver.repositories;

import net.efullstack.ecomauthorisationserver.models.Customer;
import org.springframework.data.repository.ListCrudRepository;

public interface CustomerRepository extends ListCrudRepository<Customer, Integer> {
    public Customer findCustomerByUsername(String userName);
}
