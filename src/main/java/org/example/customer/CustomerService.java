package org.example.customer;

import org.example.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerDAO customerDAO;

    public CustomerService(@Qualifier("jpa") CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    public List<Customer> getCustomers() {
        return customerDAO.getCustomers();
    }

    public Customer getCustomerById(Integer customerId) throws ResourceNotFoundException {
        return customerDAO.getCustomerById(customerId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Customer with id %s does not exist".formatted(customerId))
                );
    }
}
