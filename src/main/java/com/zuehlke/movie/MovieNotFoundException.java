package com.zuehlke.movie;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
class MovieNotFoundException extends RuntimeException {
    MovieNotFoundException(String message) {
        super(message);
    }
}
