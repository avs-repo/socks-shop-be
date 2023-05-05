package com.avs.socksshopbe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NotEnoughtItemsException extends RuntimeException {
    public NotEnoughtItemsException(String message) {
        super(message);
    }
}
