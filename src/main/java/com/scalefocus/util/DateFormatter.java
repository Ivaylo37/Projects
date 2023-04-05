package com.scalefocus.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateFormatter {

    public static LocalDate formatter(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.parse(date, formatter);
        return localDate;
    }
    public static String dateToString(LocalDate localDate) {
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dateString = localDate.format(formatters);
        return dateString;
    }
}
