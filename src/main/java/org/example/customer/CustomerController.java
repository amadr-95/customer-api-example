package org.example.customer;

import org.example.exception.ResourceNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    //@RequestMapping(path = "/customers", method = RequestMethod.GET)
    @GetMapping(path = "/api/v1/customers")
    public List<Customer> getCustomers() {
        return customerService.getCustomers();
    }

    @GetMapping(path = "/api/v1/customers/{customerId}")
    public Customer getCustomerById(@PathVariable(name = "customerId") Integer customerId) throws ResourceNotFoundException {
        return customerService.getCustomerById(customerId);
    }
}
