package org.example.customer;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerDAO customerDAO;

    public CustomerService(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    public List<Customer> getCustomers() {
        return customerDAO.getCustomers();
    }

    public Customer getCustomerById(Integer customerId) {
        return customerDAO.getCustomerById(customerId)
                .orElseThrow(
                        () -> new IllegalArgumentException("Customer with id %s does not exist".formatted(customerId))
                );
    }
}
