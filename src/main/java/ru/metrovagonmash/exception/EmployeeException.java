package ru.metrovagonmash.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmployeeException extends IllegalArgumentException {
    public EmployeeException() {
        super();
    }

    public EmployeeException(String s) {
        super(s);
    }

    public EmployeeException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmployeeException(Throwable cause) {
        super(cause);
    }
}
