package org.gig.withpet.core.exception;

/**
 * @author : JAKE
 * @date : 2022/05/24
 */
public class AlreadyEntity extends RuntimeException {

    public AlreadyEntity() {
    }

    public AlreadyEntity(String message) { super(message); }

    public AlreadyEntity(String message, Throwable cause) {
        super(message, cause);
    }

    public AlreadyEntity(Throwable cause) {
        super(cause);
    }

    public AlreadyEntity(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}