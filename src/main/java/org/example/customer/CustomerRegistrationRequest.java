package org.example.customer;

import java.time.LocalDate;

public record CustomerRegistrationRequest(
        String name,
        String email,
        LocalDate birth
) {
}
