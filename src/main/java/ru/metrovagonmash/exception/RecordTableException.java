package ru.metrovagonmash.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RecordTableException extends IllegalArgumentException {
    public RecordTableException() {
    }

    public RecordTableException(String s) {
        super(s);
    }

    public RecordTableException(String message, Throwable cause) {
        super(message, cause);
    }

    public RecordTableException(Throwable cause) {
        super(cause);
    }
}
