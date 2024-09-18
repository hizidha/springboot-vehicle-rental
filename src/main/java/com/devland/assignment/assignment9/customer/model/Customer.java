package com.devland.assignment.assignment9.customer.model;

import com.devland.assignment.assignment9.customer.model.dto.CustomerResponseDTO;
import com.devland.assignment.assignment9.rentalrecord.model.RentalRecord;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String email;
    private String phone;
    private String address;
    private String active;

    @OneToMany(mappedBy = "customer")
    private List<RentalRecord> rentalRecords;

    public String capitalize(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        String[] words = input.split("\\s+");
        StringBuilder capitalizedWords = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                capitalizedWords.append(word.substring(0, 1).toUpperCase())
                        .append(word.substring(1).toLowerCase())
                        .append(" ");
            }
        }
        return capitalizedWords.toString().trim();
    }

    public CustomerResponseDTO convertToResponse() {
        return CustomerResponseDTO.builder()
                .id(this.id)
                .name(this.name)
                .email(this.email)
                .phone(this.phone)
                .address(this.address)
                .active(this.active)
                .build();
    }
}