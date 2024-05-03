package org.example.exception;

public class ResourceDuplicateException extends CustomerException {
    public ResourceDuplicateException() {
        super();
    }

    public ResourceDuplicateException(String message) {
        super(message);
    }

    public ResourceDuplicateException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceDuplicateException(Throwable cause) {
        super(cause);
    }

    protected ResourceDuplicateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
