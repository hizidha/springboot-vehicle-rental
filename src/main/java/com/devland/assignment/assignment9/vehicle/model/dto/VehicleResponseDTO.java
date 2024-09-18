package com.devland.assignment.assignment9.vehicle.model.dto;

import com.devland.assignment.assignment9.vehicle.model.VehicleStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleResponseDTO {
    private long id;
    private String brand;
    private String model;
    private String registrationNumber;
    private VehicleStatus status;
    private String active;
}