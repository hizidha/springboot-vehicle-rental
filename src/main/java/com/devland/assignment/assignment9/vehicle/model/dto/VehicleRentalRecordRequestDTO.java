package com.devland.assignment.assignment9.vehicle.model.dto;

import com.devland.assignment.assignment9.vehicle.model.Vehicle;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleRentalRecordRequestDTO {
    @Positive(message = "ID must be positive number or not zero")
    @NotNull(message = "ID is required")
    private long id;

    public Vehicle convertToEntity() {
        return Vehicle.builder()
                .id(this.id)
                .build();
    }
}