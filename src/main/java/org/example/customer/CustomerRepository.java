package org.example.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    @Query(value = "SELECT COUNT(*) FROM customer c WHERE c.email = :email", nativeQuery = true)
    boolean existsCustomerByEmail(@Param("email") String email);
}
