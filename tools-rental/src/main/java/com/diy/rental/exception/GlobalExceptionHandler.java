package com.diy.rental.exception;

import com.diy.rental.http.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private final RequestContext requestContext;
    private final MessageSource messageSource;

    @Autowired
    public GlobalExceptionHandler(RequestContext requestContext, MessageSource messageSource) {
        this.requestContext = requestContext;
        this.messageSource = messageSource;
    }

    @ExceptionHandler(StatusCode400Exception.class)
    protected ResponseEntity<Object> handleBadDataException(StatusCode400Exception ex) {
        return processError(ex, ex.getApiError());
    }

    @ExceptionHandler(StatusCode404Exception.class)
    protected ResponseEntity<Object> handleDataNotFoundException(StatusCode404Exception ex) {
        return processError(ex, ex.getApiError());
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleAll(Exception ex) {
        List<CodeDescriptionError> errors = new ArrayList<>();
        errors.add(new CodeDescriptionError(ErrorCodeEnum.INTERNAL_SERVER_ERROR.getCode(), ErrorCodeEnum.INTERNAL_SERVER_ERROR.getDescription()));
        CodeDescriptionError codeDescriptionError = new CodeDescriptionError(ErrorCodeEnum.INTERNAL_SERVER_ERROR.getCode(), ErrorCodeEnum.INTERNAL_SERVER_ERROR.getDescription());
        final ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, errors);
        return processError(ex, apiError);
    }

    private ResponseEntity<Object> processError(Exception ex, ApiError apiError) {
        String requestBody = null != requestContext.getBody() ? requestContext.getBody().toString() : "";
        logger.error(requestBody,ex);
        apiError.setData(requestBody);
        return new ResponseEntity<>(apiError, apiError.getHttpStatus());
    }

}

