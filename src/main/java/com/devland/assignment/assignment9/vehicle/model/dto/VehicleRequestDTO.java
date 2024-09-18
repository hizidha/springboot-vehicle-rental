package com.devland.assignment.assignment9.vehicle.model.dto;

import com.devland.assignment.assignment9.vehicle.model.Vehicle;

import com.devland.assignment.assignment9.vehicle.model.VehicleStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleRequestDTO {
    private long id;

    @NotBlank(message = "branch is Required")
    private String brand;

    @NotBlank(message = "Model is Required")
    private String model;

    @NotBlank(message = "Registration Number is Required")
    private String registrationNumber;

    @Enumerated(EnumType.STRING)
    private VehicleStatus status = VehicleStatus.AVAILABLE;
    private String active = "true";

    public Vehicle convertToEntity() {
        return Vehicle.builder()
            .id(this.id)
            .brand(this.brand)
            .model(this.model)
            .registrationNumber(this.registrationNumber)
            .status(this.status)
            .active(this.active)
            .build();
    }
}