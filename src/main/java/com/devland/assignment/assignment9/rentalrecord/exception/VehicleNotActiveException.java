package com.devland.assignment.assignment9.rentalrecord.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class VehicleNotActiveException extends RuntimeException {
    public VehicleNotActiveException(String message) {
        super(message);
    }
}