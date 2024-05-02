package org.example.customer;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CustomerJpaDataAccessService implements CustomerDAO {
    private final CustomerRepository customerRepository;

    public CustomerJpaDataAccessService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> getCustomerById(Integer customerId) {
        return Optional.empty();
    }
}
