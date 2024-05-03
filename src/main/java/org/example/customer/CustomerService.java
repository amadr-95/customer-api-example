package org.example.customer;

import org.example.exception.RequestValidationException;
import org.example.exception.ResourceDuplicateException;
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
                        () -> new ResourceNotFoundException(
                                "Customer with id %s does not exist".formatted(customerId))
                );
    }

    public void createCustomer(CustomerRegistrationRequest customer) throws ResourceDuplicateException {
        //checks
        String email = customer.email();
        if (customerDAO.existCustomerByEmail(email))
            throw new ResourceDuplicateException("email already taken");

        Customer newCustomer = new Customer(
                customer.name(),
                customer.email(),
                customer.birth()
        );
        customerDAO.createCustomer(newCustomer);
    }

    public void deleteCustomerById(Integer customerId) throws ResourceNotFoundException {
        getCustomerById(customerId);
        customerDAO.deleteCustomerById(customerId);
    }

    public void updateCustomerById(Integer customerId, CustomerUpdateRequest updateCustomer)
            throws ResourceNotFoundException, ResourceDuplicateException, RequestValidationException {
        Customer customer = getCustomerById(customerId); //throws NotFoundException

        String emailRequest = updateCustomer.email();
        if (updateCustomer.email() != null && !emailRequest.equalsIgnoreCase(customer.getEmail()) &&
                customerDAO.existCustomerByEmail(emailRequest)
        ) {
            throw new ResourceDuplicateException("email already taken");
        }

        boolean changes = false;

        if (updateCustomer.name() != null && !updateCustomer.name().equalsIgnoreCase(customer.getName())) {
            changes = true;
            customer.setName(updateCustomer.name());
        }
        if (updateCustomer.email() != null && !updateCustomer.email().equalsIgnoreCase(customer.getEmail())) {
            changes = true;
            customer.setEmail(updateCustomer.email());
        }

        if (updateCustomer.birth() != null && !updateCustomer.birth().toString().equalsIgnoreCase(customer.getBirth().toString())) {
            changes = true;
            customer.setBirth(updateCustomer.birth());
        }

        if(!changes)
            throw new RequestValidationException("No data changes found");

        customerDAO.updateCustomer(customer);

    }
}
