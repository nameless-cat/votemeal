package com.agromov.votemeal.web;

import com.agromov.votemeal.util.exception.VoteNotAcceptedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import java.security.AccessControlException;

/**
 * Created by A.Gromov on 13.06.2017.
 */
@RestControllerAdvice(annotations = RestController.class)
public class ExceptionInfoHandler
{
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessControlException.class)
    public void accessControlHandle()
    {}

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public void notFoundHandle() {}

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public void illegalArgumentHandle() {}

    @ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(VoteNotAcceptedException.class)
    public void voteNotAcceptedHandle() {}

    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public void bindingErrorsHandle() {}
}
