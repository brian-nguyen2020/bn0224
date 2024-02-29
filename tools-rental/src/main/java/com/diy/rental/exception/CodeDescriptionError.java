package com.diy.rental.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CodeDescriptionError {
    private String code;
    private String description;

}
