package com.diy.rental.http;

import lombok.Data;

import java.util.Date;

@Data
public class CheckoutRequest {
    private String toolCode;
    private int rentalDays;
    private int discountPercent;
    private String checkoutDate;
}
