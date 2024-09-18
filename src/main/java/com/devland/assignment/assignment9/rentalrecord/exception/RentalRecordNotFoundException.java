package com.devland.assignment.assignment9.rentalrecord.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class RentalRecordNotFoundException extends RuntimeException {
    public RentalRecordNotFoundException(String message) {
        super(message);
    }
}