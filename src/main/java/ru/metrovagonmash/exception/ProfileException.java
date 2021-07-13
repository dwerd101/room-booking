package ru.metrovagonmash.exception;

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
