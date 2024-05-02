package org.example.customer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class CustomerConfig {

    @Bean
    public CommandLineRunner runner (CustomerRepository customerRepository) {
        return args -> {
            Customer customer1 = new Customer(
                    "customer1",
                    "customer1@example.com",
                    LocalDate.parse("1995-10-07")
            );

            Customer customer2 = new Customer(
                    "customer2",
                    "customer2@example.com",
                    LocalDate.parse("2002-08-26")
            );

            customerRepository.saveAll(List.of(customer1, customer2));
        };
    }
}
