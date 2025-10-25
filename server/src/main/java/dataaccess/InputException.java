package dataaccess;

public class InputException extends Exception {

    private final String errorCode;

    public InputException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
