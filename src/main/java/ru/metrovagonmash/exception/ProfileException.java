package ru.metrovagonmash.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProfileException extends IllegalArgumentException {
    public ProfileException() {
        super();
    }

    public ProfileException(String s) {
        super(s);
    }

    public ProfileException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProfileException(Throwable cause) {
        super(cause);
    }
}
