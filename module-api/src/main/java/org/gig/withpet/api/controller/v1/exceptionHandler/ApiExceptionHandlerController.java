package org.gig.withpet.api.controller.v1.exceptionHandler;

import lombok.extern.slf4j.Slf4j;
import org.gig.withpet.api.utils.ApiResponse;
import org.gig.withpet.core.domain.exception.DuplicationException;
import org.gig.withpet.core.domain.exception.ForbiddenException;
import org.gig.withpet.core.domain.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author : JAKE
 * @date : 2022/07/31
 */
@Slf4j
@RestControllerAdvice(annotations = {RestController.class, ResponseBody.class})
public class ApiExceptionHandlerController {

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<ApiResponse> notFoundException(NotFoundException ne) {
        return new ResponseEntity<>(ApiResponse.ERROR(ne.getStatus(), ne.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = ForbiddenException.class)
    public ResponseEntity<ApiResponse> forbiddenHandler(ForbiddenException fe) {
        return new ResponseEntity<>(ApiResponse.ERROR(fe.getStatus(), fe.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ResponseStatus(code = HttpStatus.CONFLICT)
    @ExceptionHandler(value = DuplicationException.class)
    public ResponseEntity<ApiResponse> alreadyEntityHandler(DuplicationException de) {
        return new ResponseEntity<>(ApiResponse.ERROR(de.getStatus(), de.getMessage()), HttpStatus.CONFLICT);
    }

}
