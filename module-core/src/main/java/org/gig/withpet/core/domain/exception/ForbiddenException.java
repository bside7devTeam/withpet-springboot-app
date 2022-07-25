package org.gig.withpet.core.domain.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author : JAKE
 * @date : 2022/07/25
 */
@Getter
public class ForbiddenException extends RuntimeException {

    private HttpStatus status = HttpStatus.FORBIDDEN;

    public ForbiddenException(String message) {
        super(message);
    }

    public ForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }

    public ForbiddenException(Throwable cause) {
        super(cause);
    }

    protected ForbiddenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}