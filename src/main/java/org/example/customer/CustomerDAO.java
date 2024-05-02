package org.example.customer;

import java.util.List;
import java.util.Optional;

public interface CustomerDAO {
    List<Customer> getCustomers();
    Optional<Customer> getCustomerById(Integer customerId);
    void createCustomer(Customer customer);
    boolean existCustomerByEmail(String email);
}
