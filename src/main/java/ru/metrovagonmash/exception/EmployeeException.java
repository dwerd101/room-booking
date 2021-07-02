package ru.metrovagonmash.exception;

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
