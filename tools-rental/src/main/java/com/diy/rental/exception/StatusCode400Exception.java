package com.diy.rental.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Data
public class StatusCode400Exception extends  RuntimeException {
    private  ApiError apiError;
    public StatusCode400Exception(List<CodeDescriptionError> errorList) {
        apiError = new ApiError(HttpStatus.BAD_REQUEST, errorList);
    }
}
