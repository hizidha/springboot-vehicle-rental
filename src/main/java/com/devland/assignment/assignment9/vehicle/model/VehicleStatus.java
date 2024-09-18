package com.devland.assignment.assignment9.vehicle.model;

import lombok.Getter;

@Getter
public enum VehicleStatus {
    RENT("RENT"),
    AVAILABLE("AVAILABLE");

    private final String status;

    VehicleStatus(String status) {
        this.status = status;
    }
}