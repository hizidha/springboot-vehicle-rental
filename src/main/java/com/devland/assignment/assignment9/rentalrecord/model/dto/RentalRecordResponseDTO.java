package com.devland.assignment.assignment9.rentalrecord.model.dto;

import com.devland.assignment.assignment9.customer.model.dto.CustomerResponseDTO;
import com.devland.assignment.assignment9.rentalrecord.model.RentalStatus;
import com.devland.assignment.assignment9.vehicle.model.dto.VehicleResponseDTO;
import lombok.*;

import java.sql.Date;
import java.sql.Timestamp;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RentalRecordResponseDTO {
    private long id;
    private Date rentalDate;
    private Date returnDate;
    private RentalStatus status;
    private CustomerResponseDTO customer;
    private VehicleResponseDTO vehicle;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}