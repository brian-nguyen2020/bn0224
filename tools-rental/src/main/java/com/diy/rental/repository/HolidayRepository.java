package com.diy.rental.repository;

import com.diy.rental.model.Holiday;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentMap;

@Repository
public class HolidayRepository {
    public ConcurrentMap<String, Holiday> getHolidays() {
        return MockDatabase.holidays;
    }
}
