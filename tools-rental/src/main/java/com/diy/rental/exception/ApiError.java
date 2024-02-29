package com.diy.rental.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
public class ApiError {
    private final String status = "error";
    @JsonIgnore
    private HttpStatus httpStatus;
    private List errors;
    @JsonIgnore
    private Object data;

    public ApiError(final HttpStatus httpStatus, final List<CodeDescriptionError> errors) {
       super();
        this.httpStatus = httpStatus;
        this.errors = errors;
    }
}
