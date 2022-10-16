package com.trkgrn_theomer.ecommercespring.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class ExpiredJwtExc extends RuntimeException {
    public ExpiredJwtExc(String message) {
        super(message);
    }

}
