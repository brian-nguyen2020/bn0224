package com.diy.rental.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class Holiday {
    private String name;
    private int month;
    private int day;

    public Holiday(String name, int month) {
        this.name = name;
        this.month = month;
    }
}
