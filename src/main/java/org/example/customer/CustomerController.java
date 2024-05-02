package org.example.customer;

import org.example.exception.ResourceDuplicateException;
import org.example.exception.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping(path = "/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    //@RequestMapping(path = "/customers", method = RequestMethod.GET)
    @GetMapping
    public List<Customer> getCustomers() {
        return customerService.getCustomers();
    }

    @GetMapping(path = "/{customerId}")
    public Customer getCustomerById(@PathVariable(name = "customerId") Integer customerId)
            throws ResourceNotFoundException {
        return customerService.getCustomerById(customerId);
    }

    @PostMapping
    public void createCustomer(@RequestBody CustomerRegistrationRequest customerRequest)
            throws ResourceDuplicateException {
        customerService.createCustomer(customerRequest);
    }
}
