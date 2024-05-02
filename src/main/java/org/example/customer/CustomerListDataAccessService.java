package org.example.customer;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CustomerListDataAccessService implements CustomerDAO {
    //fake db
    private final static List<Customer> customers;

    static {
        Customer customer1 = new Customer(
                1,
                "Amador",
                "amador@example.com",
                LocalDate.parse("1995-10-07")
        );

        Customer customer2 = new Customer(
                2,
                "Sandra",
                "sandra@example.com",
                LocalDate.parse("2002-08-26")
        );
        customers = new ArrayList<>(List.of(customer1, customer2));
    }

    @Override
    public List<Customer> getCustomers() {
        return customers;
    }

    @Override
    public Optional<Customer> getCustomerById(Integer customerId) {
        return customers.stream()
                .filter(customer -> customer.getId().equals(customerId))
                .findFirst();
    }
}