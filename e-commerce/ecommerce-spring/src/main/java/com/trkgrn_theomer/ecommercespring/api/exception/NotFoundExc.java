package com.trkgrn_theomer.ecommercespring.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundExc extends RuntimeException{
    public NotFoundExc(String message) {
        super(message);
    }
}
