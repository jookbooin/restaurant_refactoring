package com.restaurant.reservation.common.exception.domain;

public class CategoryException extends DomainException {

    public CategoryException() {
        super();
    }

    public CategoryException(String message) {
        super(message);
    }

    public CategoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public CategoryException(Throwable cause) {
        super(cause);
    }

    protected CategoryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
