package com.devland.assignment.assignment9.customer.model.dto;

import com.devland.assignment.assignment9.customer.model.Customer;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRentalRecordRequestDTO {
    @Positive(message = "ID must be positive number or not zero")
    @NotNull(message = "ID is required")
    private long id;

    public Customer convertToEntity() {
        return Customer.builder()
                .id(this.id)
                .build();
    }
}