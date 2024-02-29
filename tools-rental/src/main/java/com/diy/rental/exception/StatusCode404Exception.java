package com.diy.rental.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;
@Data
public class StatusCode404Exception extends  RuntimeException {
    private  ApiError apiError;
    public StatusCode404Exception(List<CodeDescriptionError> errorList) {
        apiError = new ApiError(HttpStatus.NOT_FOUND, errorList);
    }
}
