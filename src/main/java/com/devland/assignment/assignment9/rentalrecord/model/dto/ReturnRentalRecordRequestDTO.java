package com.devland.assignment.assignment9.rentalrecord.model.dto;

import com.devland.assignment.assignment9.rentalrecord.model.RentalRecord;
import com.devland.assignment.assignment9.rentalrecord.model.RentalStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReturnRentalRecordRequestDTO {
    @NotNull(message = "Return date is required")
    @PastOrPresent(message = "Return date cannot be in the future")
    private Date returnDate;

    private RentalStatus status = RentalStatus.RETURNED;

    public RentalRecord convertToEntity() {
        return RentalRecord.builder()
                .returnDate(this.returnDate)
                .status(this.status)
                .build();
    }
}