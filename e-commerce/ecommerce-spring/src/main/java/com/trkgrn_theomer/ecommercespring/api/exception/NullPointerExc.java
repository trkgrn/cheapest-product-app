package com.trkgrn_theomer.ecommercespring.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NullPointerExc extends RuntimeException{
    public NullPointerExc(String message) {
        super(message);
    }
}
