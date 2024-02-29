package com.diy.rental.repository;

import com.diy.rental.exception.StatusCode400Exception;
import com.diy.rental.model.AvailableTool;
import com.diy.rental.model.Holiday;
import com.diy.rental.model.RentalCharge;
import lombok.Data;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
@Data
public class MockDatabase {

    public static  ConcurrentMap<String, AvailableTool> availableTools;
    public static ConcurrentMap<String, RentalCharge> rentalCharges;
    public static ConcurrentMap<String, Holiday> holidays;
    private static void createAvailableToolRecords()  {
        availableTools = new ConcurrentHashMap<>(4);
        availableTools.put("CHNS", new AvailableTool("CHNS", "Chainsaw", "Stihl"));
        availableTools.put("LADW", new AvailableTool("LADW","Ladder","Werner"));
        availableTools.put("JAKD", new AvailableTool("JAKD","Jackhammer","DeWalt"));
        availableTools.put("JAKR", new AvailableTool("JAKR","Jackhammerr","Ridgid"));
    }

    private static void createRentalChargeRecords() {
        rentalCharges = new ConcurrentHashMap<>(3);
        rentalCharges.put("Ladder", new RentalCharge("Ladder", new BigDecimal(1.99), true, true, false));
        rentalCharges.put("Chainsaw", new RentalCharge("Chainsaw", new BigDecimal(1.49), true, false, true));
        rentalCharges.put("Jackhammer", new RentalCharge("Jackhammer", new BigDecimal(2.99), true, false, false));
    }

    private static void createHolidays() {
        holidays = new ConcurrentHashMap<>(2);
        holidays.put("Independence", new Holiday("Independence",7, 4));
        holidays.put("Labor", new Holiday("Labor",9 , 1));


    }

    public static void createDataBaseRecords() {
        createAvailableToolRecords();
        createRentalChargeRecords();
        createHolidays();

    }
}







