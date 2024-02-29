package com.diy.rental.model;

import lombok.Data;

import java.util.Date;

@Data
public class Checkout {
    private String toolCode;
    private int rentalDayCount;
    private int discountPercent;
    private String checkOutDate; // "mm/dd/yy"
}
