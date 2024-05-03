package org.example.customer;

import java.time.LocalDate;

public record CustomerUpdateRequest(
        String name,
        String email,
        LocalDate birth
) {
}
