package org.example.customer;

import org.example.exception.CustomerException;
import org.example.exception.RequestValidationException;
import org.example.exception.ResourceDuplicateException;
import org.example.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerDAO customerDAO;

    public CustomerService(@Qualifier("list") CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    public List<Customer> getCustomers() {
        return customerDAO.getCustomers();
    }

    public Customer getCustomerById(Integer customerId) throws ResourceNotFoundException {
        return customerDAO.getCustomerById(customerId)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Customer with id %s does not exist".formatted(customerId))
                );
    }

    public void createCustomer(CustomerRegistrationRequest customerRequest) throws CustomerException {
        if (customerRequest == null)
            throw new CustomerException("customer is null");

        validateCustomerFields(customerRequest);

        existsCustomerByEmail(customerRequest.email());

        Customer newCustomer = new Customer(
                customerRequest.name(),
                customerRequest.email(),
                customerRequest.birth()
        );
        customerDAO.createCustomer(newCustomer);
    }

    public void deleteCustomerById(Integer customerId) throws ResourceNotFoundException {
        getCustomerById(customerId);
        customerDAO.deleteCustomerById(customerId);
    }

    public void updateCustomerById(Integer customerId, CustomerUpdateRequest updateCustomer)
            throws CustomerException {
        Customer customer = getCustomerById(customerId); //throws NotFoundException

        boolean changes = false;

        if (updateCustomer.name() != null && !updateCustomer.name().equalsIgnoreCase(customer.getName())) {
            changes = true;
            customer.setName(updateCustomer.name());
        }
        if (updateCustomer.email() != null && !updateCustomer.email().equalsIgnoreCase(customer.getEmail())) {
            existsCustomerByEmail(updateCustomer.email());

            changes = true;
            customer.setEmail(updateCustomer.email());
        }

        if (updateCustomer.birth() != null &&
                !updateCustomer.birth().toString().equalsIgnoreCase(customer.getBirth().toString())) {
            changes = true;
            customer.setBirth(updateCustomer.birth());
        }

        if (!changes)
            throw new RequestValidationException("No data changes found");

        customerDAO.updateCustomer(customer);
    }

    private static void validateCustomerFields(CustomerRegistrationRequest customer) throws RequestValidationException {
        if(
                customer.name() == null || customer.name().isBlank() ||
                        customer.email() == null || customer.email().isBlank()
                        || customer.birth() == null || customer.birth().toString().isBlank()
        )
            throw new RequestValidationException("missing field/s");
    }

    private void existsCustomerByEmail(String email) throws ResourceDuplicateException {
        if (customerDAO.existCustomerByEmail(email))
            throw new ResourceDuplicateException("email already taken");
    }
}
