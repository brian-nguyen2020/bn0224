package com.diy.rental.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateFormatter {

    public static final String getDateWithMMDDYYFromYYYYMMDD(String fromFormattedDate) throws Exception{
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd");
        Date toFormattedDate = simpleDateFormat.parse(fromFormattedDate.toString());
        simpleDateFormat.applyPattern("mm/dd/yy");
        return simpleDateFormat.format(toFormattedDate);
    }

    public static final String getDateWithYYYYMMDDFromMMDDYY(String fromFormattedDate) throws Exception{
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm/dd/yy");
        Date toFormattedDate = simpleDateFormat.parse(fromFormattedDate.toString());
        simpleDateFormat.applyPattern("yyyy-mm-dd");
        return simpleDateFormat.format(toFormattedDate);
    }

}
