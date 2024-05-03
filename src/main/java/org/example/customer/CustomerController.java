package org.example.customer;

import org.example.exception.CustomerException;
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
            throws CustomerException {
        customerService.createCustomer(customerRequest);
    }

    @DeleteMapping("/{customerId}")
    public void deleteCustomerById(@PathVariable(name = "customerId") Integer customerId)
            throws ResourceNotFoundException {
        customerService.deleteCustomerById(customerId);
    }

    @PutMapping("/{customerId}")
    public void updateCustomerById(@PathVariable(name = "customerId") Integer customerId,
                                   @RequestBody CustomerUpdateRequest customer)
            throws CustomerException {
        customerService.updateCustomerById(customerId, customer);
    }
}
