package ru.metrovagonmash.exception;

public class DepartmentException extends IllegalArgumentException {
    public DepartmentException() {
        super();
    }

    public DepartmentException(String s) {
        super(s);
    }

    public DepartmentException(String message, Throwable cause) {
        super(message, cause);
    }

    public DepartmentException(Throwable cause) {
        super(cause);
    }
}
