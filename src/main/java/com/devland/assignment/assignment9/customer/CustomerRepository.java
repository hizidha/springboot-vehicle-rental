package com.devland.assignment.assignment9.customer;

import com.devland.assignment.assignment9.customer.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("SELECT c FROM Customer c WHERE c.name = :name AND c.active = 'true'")
    Optional<Customer> findByNameIsActive(@Param("name") String name);

    Page<Customer> findAllByActive(String active, Pageable pageable);

    Page<Customer> findAllByNameContainsIgnoreCase(String name, Pageable pageable);

    Page<Customer> findAllByNameContainsIgnoreCaseAndActive(String name, String active, Pageable pageable);

    @Query("SELECT COUNT(r) FROM RentalRecord r WHERE r.customer.id = :id AND r.status = 'RENT'")
    int countRentalRecordByCustomer(@Param("id") long id);
}