package com.avs.socksshopbe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NotEnoughItemsException extends IllegalArgumentException {
    public NotEnoughItemsException(String message) {
        super(message);
    }
}
