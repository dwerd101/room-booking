package ru.metrovagonmash.exception;

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
