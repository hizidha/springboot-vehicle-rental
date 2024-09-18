package com.devland.assignment.assignment9.customer;

import com.devland.assignment.assignment9.customer.exception.CustomerAlreadyExistException;
import com.devland.assignment.assignment9.customer.exception.CustomerNotFoundException;
import com.devland.assignment.assignment9.customer.exception.CustomerStillHaveRentalRecordException;
import com.devland.assignment.assignment9.customer.model.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public Customer getOneBy(long id) {
        return this.customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer with ID " + id + " not found"));
    }

    public Page<Customer> findAll(Optional<String> optionalName, Optional<String> optionalStatus, Pageable pageable) {
        if (optionalStatus.isPresent() && optionalName.isEmpty()) {
            return this.customerRepository.findAllByActive(optionalStatus.get(), pageable);
        }

        if (optionalName.isPresent()) {
            if (optionalStatus.isPresent()) {
                return this.customerRepository.findAllByNameContainsIgnoreCaseAndActive(optionalName.get(), optionalStatus.get(), pageable);
            }
            return this.customerRepository.findAllByNameContainsIgnoreCase(optionalName.get(), pageable);
        }
        return this.customerRepository.findAll(pageable);
    }

    public void delete(long id) {
        int existingRentalRecord = this.customerRepository.countRentalRecordByCustomer(id);

        if(existingRentalRecord > 0) {
            throw new CustomerStillHaveRentalRecordException("Customer with ID " + id + ", still has unreturned rental records");
        }

        Customer existingCustomer = this.getOneBy(id);
        if(!Objects.equals(existingCustomer.getActive(), "true")) {
            throw new CustomerNotFoundException("Customer with ID " + id + " already inactive");
        }
        existingCustomer.setActive("false");

        this.customerRepository.save(existingCustomer);
    }

    public Customer create(Customer newCustomer) {
        Optional<Customer> existingCustomer = this.customerRepository.findByNameIsActive(newCustomer.getName());

        if (existingCustomer.isPresent()) {
            throw new CustomerAlreadyExistException("Customer with name " + newCustomer.getName() + " already exist");
        }
        newCustomer.setName(newCustomer.capitalize(newCustomer.getName()));
        newCustomer.setEmail(newCustomer.getEmail().toLowerCase());

        return this.customerRepository.save(newCustomer);
    }

    public Customer update(Customer updatedCustomer) {
        Customer existingCustomer = this.getOneBy(updatedCustomer.getId());

        updatedCustomer.setId(existingCustomer.getId());
        updatedCustomer.setName(updatedCustomer.capitalize(updatedCustomer.getName()));
        updatedCustomer.setEmail(updatedCustomer.getEmail().toLowerCase());

        return this.customerRepository.save(updatedCustomer);
    }
}