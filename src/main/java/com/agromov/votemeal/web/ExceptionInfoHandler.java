package com.agromov.votemeal.web;

import com.agromov.votemeal.util.ValidationUtils;
import com.agromov.votemeal.util.exception.BadArgumentException;
import com.agromov.votemeal.util.exception.ErrorInfo;
import com.agromov.votemeal.util.exception.NotFoundException;
import com.agromov.votemeal.util.exception.VoteNotAcceptedException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.security.AccessControlException;
import java.util.Arrays;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by A.Gromov on 13.06.2017.
 */
@RestControllerAdvice(annotations = RestController.class)
public class ExceptionInfoHandler
{
    private static final Logger LOG = getLogger(ExceptionInfoHandler.class);

    @Autowired
    private MessageUtils messageUtils;

    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessControlException.class)
    public ErrorInfo accessControlHandle(HttpServletRequest request, AccessControlException e)
    {
        return logAndGetErrorInfo(request, "AccessControlException", messageUtils.getMessage(e.getMessage()));
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ErrorInfo notFoundHandle(HttpServletRequest request, NotFoundException e)
    {
        return logAndGetErrorInfo(request, "NotFoundException", messageUtils.getMessage(e.getMessage()));
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadArgumentException.class)
    public ErrorInfo illegalArgumentHandle(HttpServletRequest request, BadArgumentException e)
    {
        return logAndGetErrorInfo(request, "BadArgumentException", messageUtils.getMessage(e.getMessage()));
    }

    @ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(VoteNotAcceptedException.class)
    public ErrorInfo voteNotAcceptedHandle(HttpServletRequest request, VoteNotAcceptedException e)
    {
        return logAndGetErrorInfo(request, "VoteException", messageUtils.getMessage(e.getMessage()));
    }

    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorInfo bindingErrorsHandle(HttpServletRequest request, MethodArgumentNotValidException e)
    {
        return logAndGetValidationErrorInfo(request, e.getBindingResult());
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    public void exceptionHandle()
    {
    }

    private ErrorInfo logAndGetValidationErrorInfo(HttpServletRequest req, BindingResult result)
    {
        String[] details = result.getAllErrors().stream()
                .map(fe -> messageUtils.getMessage(fe))
                .toArray(String[]::new);

        return logAndGetErrorInfo(req, "ValidationException", details);
    }

    private static ErrorInfo logAndGetErrorInfo(HttpServletRequest req, String cause, String... details) {
        LOG.warn("{} exception at request {}: {}", cause, req.getRequestURL(), Arrays.toString(details));
        return new ErrorInfo(req.getRequestURL(), cause, details);
    }
}
