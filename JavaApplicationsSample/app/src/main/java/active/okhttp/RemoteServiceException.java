package active.okhttp;

public class RemoteServiceException extends RuntimeException {
    public RemoteServiceException(Exception e) {
        super(e);
    }

    public RemoteServiceException(String message) {
        super(message);
    }

    public RemoteServiceException(String message, Exception e) {
        super(message, e);
    }
}
