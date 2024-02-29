package com.diy.rental.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AvailableTool {
    private String toolCode;
    private String toolType;
    private String toolBrand;
}
