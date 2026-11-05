package dataaccess;

public class DataAccessException extends Exception {

    private final String errorCode;

    public DataAccessException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
