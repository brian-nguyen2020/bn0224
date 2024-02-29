package com.diy.rental.repository;

import com.diy.rental.model.RentalCharge;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentMap;

@Repository
public class RentalChargeRepository {
    ConcurrentMap<String, RentalCharge> rentalCharges = MockDatabase.rentalCharges;
    public ConcurrentMap<String, RentalCharge> getRentalCharge() {
        return MockDatabase.rentalCharges;
    }

    public BigDecimal getDailyRentalCharge(String toolType) {
        return rentalCharges.get(toolType).getDailyCharge();
    }

    public RentalCharge getRentalChargeByToolType(String toolType) {
        return rentalCharges.get(toolType);
    }
}
