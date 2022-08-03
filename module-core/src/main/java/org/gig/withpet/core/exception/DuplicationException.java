package org.gig.withpet.core.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author : JAKE
 * @date : 2022/07/31
 */
@Getter
public class DuplicationException extends RuntimeException {

    private HttpStatus status = HttpStatus.CONFLICT;

    public DuplicationException(String message) {
        super(message);
    }

    public DuplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicationException(Throwable cause) {
        super(cause);
    }

    protected DuplicationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
