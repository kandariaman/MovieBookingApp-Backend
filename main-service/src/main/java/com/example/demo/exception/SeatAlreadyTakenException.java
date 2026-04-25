package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class SeatAlreadyTakenException extends RuntimeException{

    public SeatAlreadyTakenException() {
        super("This seat has already been reserved or booked by another user.");
    }

    public SeatAlreadyTakenException(String message) {
        super(message);
    }
}
