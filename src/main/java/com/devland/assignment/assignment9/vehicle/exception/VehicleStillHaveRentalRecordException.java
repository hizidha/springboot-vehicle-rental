package com.devland.assignment.assignment9.vehicle.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class VehicleStillHaveRentalRecordException extends RuntimeException {
    public VehicleStillHaveRentalRecordException(String message){
        super(message);
    }
}