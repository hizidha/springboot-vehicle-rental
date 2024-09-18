package com.devland.assignment.assignment9.rentalrecord.model;

import lombok.Getter;

@Getter
public enum RentalStatus {
    RENT("RENT"),
    RETURNED("RETURNED");

    private final String status;

    RentalStatus(String status) {
        this.status = status;
    }
}