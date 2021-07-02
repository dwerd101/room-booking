package ru.metrovagonmash.exception;

public class VscRoomException extends IllegalArgumentException {
    public VscRoomException() {
        super();
    }

    public VscRoomException(String s) {
        super(s);
    }

    public VscRoomException(String message, Throwable cause) {
        super(message, cause);
    }

    public VscRoomException(Throwable cause) {
        super(cause);
    }
}
