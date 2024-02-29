package com.diy.rental.service;

import com.diy.rental.http.CheckoutRequest;
import com.diy.rental.model.Holiday;
import com.diy.rental.view.RentalAgreement;
import com.diy.rental.model.RentalCharge;
import com.diy.rental.repository.AvailableToolRepository;
import com.diy.rental.repository.HolidayRepository;
import com.diy.rental.repository.RentalChargeRepository;
import com.diy.rental.util.DateFormatter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Year;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RentalAgreementService {
    private final AvailableToolRepository availableToolRepository;
    private final HolidayRepository holidayRepository;
    private final RentalChargeRepository rentalChargeRepository;


    @Autowired
    public RentalAgreementService(AvailableToolRepository availableToolRepository,
                                  HolidayRepository holidayRepository,
                                  RentalChargeRepository rentalChargeRepository){
        this.availableToolRepository= availableToolRepository;
        this.holidayRepository = holidayRepository;
        this.rentalChargeRepository = rentalChargeRepository;

    }

    public void printRentalAgreement(RentalAgreement rentalAgreement) {
        log.debug("Enter printRentalAgreement with {} ", rentalAgreement);
        System.out.println("Rental Agreement");
        System.out.println("Tool code: " + rentalAgreement.getToolCode());
        System.out.println("Tool type: " + rentalAgreement.getToolType());
        System.out.println("Tool brand: " + rentalAgreement.getToolBrand());
        System.out.println("Rental days: " + rentalAgreement.getRentalDays());
        System.out.println("Check out date: " + rentalAgreement.getCheckoutDate());
        System.out.println("Due date: " + rentalAgreement.getDueDate());
        System.out.println("Daily rental charge: " + rentalAgreement.getDailyRentalCharge());
        System.out.println("Charge days: " + rentalAgreement.getChargeDays());
        System.out.println("Pre-discount charge: " + rentalAgreement.getPreDiscountCharge());
        System.out.println("Discount percent: " + rentalAgreement.getDiscountPercent());
        System.out.println("Discount amount: " + rentalAgreement.getDiscountAmount());
        System.out.println("Final charge: " + rentalAgreement.getFinalCharge());
    }
    protected RentalAgreement createRentalAgreement(CheckoutRequest checkoutRequest) throws Exception {
        log.debug("Enter createRentalAgreement() with {} ", checkoutRequest);
        String toolType = availableToolRepository.getToolType(checkoutRequest.getToolCode());
        String toolBrand = availableToolRepository.getToolBrand(checkoutRequest.getToolCode());
        LocalDate dueDate = getDueDate(checkoutRequest.getCheckoutDate(), checkoutRequest.getRentalDays());
        LocalDate checkoutDate = LocalDate.parse(DateFormatter.getDateWithYYYYMMDDFromMMDDYY(checkoutRequest.getCheckoutDate()));
        int chargeDays = countChargeDays(checkoutRequest.getToolCode(), checkoutDate,dueDate).size();
        BigDecimal rentalDailyCharge = rentalChargeRepository.getDailyRentalCharge(toolType);
        BigDecimal preDiscountCharge = calculatePreDiscountCharge(chargeDays,rentalDailyCharge);
        BigDecimal discountAmount = calculateDiscountAmount(preDiscountCharge, checkoutRequest.getDiscountPercent());
        BigDecimal finalCharge = calculateFinalCharge(preDiscountCharge, discountAmount);

        RentalAgreement rentalAgreement = RentalAgreement.builder()
                .toolCode(checkoutRequest.getToolCode())
                .toolType(toolType)
                .toolBrand(toolBrand)
                .rentalDays(checkoutRequest.getRentalDays())
                .checkoutDate(checkoutRequest.getCheckoutDate())
                .dueDate(DateFormatter.getDateWithMMDDYYFromYYYYMMDD(dueDate.toString()))
                .dailyRentalCharge(this.getDailyRentalCharge(checkoutRequest.getToolCode()))
                .chargeDays(chargeDays)
                .preDiscountCharge(NumberFormat.getCurrencyInstance().format(preDiscountCharge))
                .discountPercent(checkoutRequest.getDiscountPercent() + "%")
                .discountAmount(NumberFormat.getCurrencyInstance().format(discountAmount))
                .finalCharge(NumberFormat.getCurrencyInstance().format(finalCharge))
                .build();
        log.info("Finish createRentalAgreement(CheckoutRequest checkoutRequest)");
        return rentalAgreement;
    }

    private BigDecimal calculateFinalCharge(BigDecimal preDiscountCharge, BigDecimal discountAmount) {
        return preDiscountCharge.subtract(discountAmount);
    }
    private BigDecimal calculateDiscountAmount(BigDecimal preDiscountCharge, int discountPercent) {
        return preDiscountCharge.multiply(BigDecimal.valueOf(discountPercent).divide(BigDecimal.valueOf(100)).setScale(2, RoundingMode.HALF_UP));
    }
    private BigDecimal calculatePreDiscountCharge(int chargeDays, BigDecimal dailyCharge) {
        return dailyCharge.multiply(BigDecimal.valueOf(chargeDays)).setScale(2, RoundingMode.HALF_UP);
    }

    private String getDailyRentalCharge(String toolCode) {
        String toolType = availableToolRepository.getToolType(toolCode);
        BigDecimal dailyRentalCharge = rentalChargeRepository.getRentalChargeByToolType(toolType).getDailyCharge();
        return NumberFormat.getCurrencyInstance().format(dailyRentalCharge);
    }


    private List<LocalDate> countChargeDays(String toolCode, LocalDate checkoutDate, LocalDate dueDate) {
        String toolType = availableToolRepository.getToolType(toolCode);
        RentalCharge rentalChargeByTool = rentalChargeRepository.getRentalChargeByToolType(toolType);
        System.out.println("rentalChargeByTool: " + rentalChargeByTool);
        ConcurrentMap<String , Holiday> holidayss = holidayRepository.getHolidays();
        Optional<List<LocalDate>> holidays = createHolidays(holidayss);
        List<LocalDate> chargeDays = new ArrayList<LocalDate>();

        Predicate<LocalDate> isHoliday = date -> holidays.isPresent()
                && holidays.get()
                .contains(date);

        Predicate<LocalDate> isWeekend = date -> date
                .getDayOfWeek() == DayOfWeek.SATURDAY
                || date.getDayOfWeek() == DayOfWeek.SUNDAY;


         if((rentalChargeByTool.isHolidayCharge() == false) && (rentalChargeByTool.isWeekendCharge() == false)) {
             chargeDays = checkoutDate.datesUntil(dueDate)
                     .filter(isWeekend.or(isHoliday)
                             .negate())
                     .collect(Collectors.toList());
        }
         else if ((rentalChargeByTool.isHolidayCharge() == false) && (rentalChargeByTool.isWeekendCharge() == true)) {
            chargeDays = checkoutDate.datesUntil(dueDate)
                    .filter(isHoliday.negate())
                    .collect(Collectors.toList());
        }
         else if ((rentalChargeByTool.isHolidayCharge() == true) && (rentalChargeByTool.isWeekendCharge() == false)) {
             chargeDays = checkoutDate.datesUntil(dueDate)
                     .filter(isWeekend.negate())
                     .collect(Collectors.toList());
         }

        return chargeDays;
    }

    private LocalDate createLaborDay(Holiday laborDay) {
        LocalDate date = LocalDate.of(Year.now().getValue(), laborDay.getMonth(),01);
        return date.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
    }

    private LocalDate createIndependenceDay(Holiday independenceDay) {
            int year = Year.now().getValue();
            LocalDate date = LocalDate.of(year, independenceDay.getMonth(),independenceDay.getDay());
            LocalDate observedDay = null;
            int dayOfWeek = date.getDayOfWeek().getValue();
            if(dayOfWeek == 6) {
                observedDay = LocalDate.of(year, independenceDay.getMonth(), independenceDay.getDay() - 1 );
            }
            else if (dayOfWeek == 0) {
                observedDay = LocalDate.of(year, independenceDay.getMonth(), independenceDay.getDay() + 1 );
            }
            else {
                observedDay = date;
            }
        return observedDay;
    }

    private Optional<List<LocalDate>> createHolidays(ConcurrentMap<String, Holiday> holidays) {
        Holiday independence = holidays.get("Independence");
        Holiday labor = holidays.get("Labor");
        List<LocalDate> actualDays = new ArrayList<>();
        actualDays.add(createLaborDay(labor));
        actualDays.add(createIndependenceDay(independence));
        return Optional.of(actualDays);
    }


    private LocalDate getDueDate(String checkoutDate, int rentDays) throws Exception {
        String checkoutD = DateFormatter.getDateWithYYYYMMDDFromMMDDYY(checkoutDate);
        return LocalDate.parse(checkoutD).plusDays(rentDays);
    }


}
