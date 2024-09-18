package com.devland.assignment.assignment9.vehicle.model;

import java.util.List;

import com.devland.assignment.assignment9.rentalrecord.model.RentalRecord;
import com.devland.assignment.assignment9.vehicle.model.dto.VehicleResponseDTO;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String brand;
    private String model;
    private String registrationNumber;
    private String active = "true";

    @Enumerated(EnumType.STRING)
    private VehicleStatus status;

    @OneToMany(mappedBy = "vehicle")
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

    public VehicleResponseDTO convertToResponse() {
        return VehicleResponseDTO.builder()
                .id(this.id)
                .brand(this.brand)
                .model(this.model)
                .registrationNumber(this.registrationNumber)
                .status(this.status)
                .active(this.active)
                .build();
    }
}