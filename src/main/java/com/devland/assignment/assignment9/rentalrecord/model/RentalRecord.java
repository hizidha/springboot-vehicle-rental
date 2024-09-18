package com.devland.assignment.assignment9.rentalrecord.model;

import com.devland.assignment.assignment9.customer.model.Customer;
import com.devland.assignment.assignment9.customer.model.dto.CustomerResponseDTO;
import com.devland.assignment.assignment9.rentalrecord.model.dto.RentalRecordResponseDTO;
import com.devland.assignment.assignment9.vehicle.model.Vehicle;
import com.devland.assignment.assignment9.vehicle.model.dto.VehicleResponseDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RentalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Enumerated(EnumType.STRING)
    private RentalStatus status;

    private Date rentalDate;
    private Date returnDate;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    public RentalRecordResponseDTO convertToResponse() {
        CustomerResponseDTO customerResponseDTO = this.customer.convertToResponse();
        VehicleResponseDTO vehicleResponseDTO = this.vehicle.convertToResponse();

        return RentalRecordResponseDTO.builder()
                .id(this.id)
                .customer(customerResponseDTO)
                .vehicle(vehicleResponseDTO)
                .rentalDate(this.rentalDate)
                .returnDate(this.returnDate)
                .status(this.status)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .build();
    }
}