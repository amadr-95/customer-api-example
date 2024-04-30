package org.example.customer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
public class CustomerController {

    private CustomerDAO db = new CustomerDAO();

    //@RequestMapping(path = "/customers", method = RequestMethod.GET)
    @GetMapping(path = "/api/v1/customers")
    public List<Customer> getCustomers() {
        return db.getCustomers();
    }

    @GetMapping(path = "/api/v1/customers/{customerId}")
    public Customer getCustomer(
            @PathVariable(name = "customerId") Integer customerId) {
        return getCustomers().stream()
                .filter(customer -> customer.getId().equals(customerId))
                .findFirst()
                .orElseThrow(
                        () -> new IllegalArgumentException("Customer with id %s does not exist".formatted(customerId))
                );
    }
}
