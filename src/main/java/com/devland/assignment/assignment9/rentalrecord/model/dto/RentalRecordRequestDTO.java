package com.devland.assignment.assignment9.rentalrecord.model.dto;

import com.devland.assignment.assignment9.vehicle.model.Vehicle;
import com.devland.assignment.assignment9.customer.model.Customer;
import com.devland.assignment.assignment9.rentalrecord.model.RentalStatus;
import com.devland.assignment.assignment9.rentalrecord.model.RentalRecord;
import com.devland.assignment.assignment9.vehicle.model.dto.VehicleRentalRecordRequestDTO;
import com.devland.assignment.assignment9.customer.model.dto.CustomerRentalRecordRequestDTO;

import java.sql.Date;
import java.sql.Timestamp;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.Valid;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RentalRecordRequestDTO {
    private long id;

    private Timestamp createdAt;
    private Timestamp updatedAt;

    @Enumerated(EnumType.STRING)
    private RentalStatus status = RentalStatus.RENT;

    @NotNull(message = "Rental date is required")
    @PastOrPresent(message = "Rental date cannot be in the future")
    private Date rentalDate;

    private Date returnDate;

    @Valid
    private CustomerRentalRecordRequestDTO customer;

    @Valid
    private VehicleRentalRecordRequestDTO vehicle;

    public RentalRecord convertToEntity() {
        Customer existingCustomer = this.customer.convertToEntity();
        Vehicle existingVehicle = this.vehicle.convertToEntity();

        return RentalRecord.builder()
                .id(this.id)
                .rentalDate(this.rentalDate)
                .returnDate(this.returnDate)
                .status(this.status)
                .customer(existingCustomer)
                .vehicle(existingVehicle)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .build();
    }
}