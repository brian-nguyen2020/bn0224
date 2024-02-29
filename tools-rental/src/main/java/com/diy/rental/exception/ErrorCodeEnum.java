package com.diy.rental.exception;


public enum ErrorCodeEnum {
    // Http request data exceptions
    INVALID_RENTAL_DAYS("1001", "Rental days input was invalid. It must be greater than 0."),
    INVALID_DISCOUNT_PERCENT("1002", "Discount percent input was invalid. It must be in the range 0-100."),
    // Exceptions in code
    INTERNAL_SERVER_ERROR("5001", "Internal Server Error"),
    ;


    private String code;
    private String description;

    private ErrorCodeEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getDescription() {return this.description;}
    public String getCode() {return this.code;}
}

