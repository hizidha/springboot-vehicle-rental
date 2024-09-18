package com.devland.assignment.assignment9;

import com.devland.assignment.assignment9.customer.CustomerService;
import com.devland.assignment.assignment9.customer.model.Customer;
import com.devland.assignment.assignment9.customer.model.dto.CustomerRequestDTO;
import com.devland.assignment.assignment9.customer.model.dto.CustomerResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping()
    public ResponseEntity<Page<CustomerResponseDTO>> getAll(
            @RequestParam(value = "name") Optional<String> optionalName,
            @RequestParam(value = "status") Optional<String> optionalStatus,
            @RequestParam(value = "sort", defaultValue = "ASC") String sortString,
            @RequestParam(value = "order_by", defaultValue = "id") String orderBy,
            @RequestParam(value = "limit", defaultValue = "5") int limit,
            @RequestParam(value = "page", defaultValue = "1") int page
    ) {
        Sort sort = Sort.by(Sort.Direction.valueOf(sortString.toUpperCase()), orderBy);
        Pageable pageable = PageRequest.of(page - 1, limit, sort);

        Page<Customer> pageCustomers = this.customerService.findAll(optionalName, optionalStatus, pageable);
        Page<CustomerResponseDTO> customerResponseDTOs = pageCustomers.map(Customer::convertToResponse);

        return ResponseEntity.ok(customerResponseDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> getOne(@PathVariable("id") long id) {
        Customer existingCustomer = this.customerService.getOneBy(id);
        CustomerResponseDTO customerResponseDTO = existingCustomer.convertToResponse();

        return ResponseEntity.ok(customerResponseDTO);
    }

    @PostMapping()
    public ResponseEntity<CustomerResponseDTO> create(
            @RequestBody @Valid CustomerRequestDTO customerRequestDTO) {
        Customer newCustomer = customerRequestDTO.convertToEntity();
        Customer savedCustomer = this.customerService.create(newCustomer);
        CustomerResponseDTO customerResponseDTO = savedCustomer.convertToResponse();

        return ResponseEntity.status(HttpStatus.CREATED).body(customerResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> update(
            @PathVariable("id") long id,
            @RequestBody @Valid CustomerRequestDTO customerRequestDTO) {
        Customer updatedCustomer = customerRequestDTO.convertToEntity();
        updatedCustomer.setId(id);

        Customer savedCustomer = this.customerService.update(updatedCustomer);
        CustomerResponseDTO customerResponseDTO = savedCustomer.convertToResponse();

        return ResponseEntity.ok().body(customerResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        this.customerService.delete(id);

        return ResponseEntity.ok().build();
    }
}