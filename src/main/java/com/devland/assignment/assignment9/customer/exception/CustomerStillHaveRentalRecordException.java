package com.devland.assignment.assignment9.customer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class CustomerStillHaveRentalRecordException extends RuntimeException {
    public CustomerStillHaveRentalRecordException(String message) {
        super(message);
    }
}