package com.trkgrn_theomer.cheapspring.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SQLExc extends RuntimeException  {
    public SQLExc(String message) {
        super(message);
    }
}
