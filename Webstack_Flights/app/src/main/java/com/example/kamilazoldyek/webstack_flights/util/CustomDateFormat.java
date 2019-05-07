package com.example.kamilazoldyek.webstack_flights.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CustomDateFormat {

    public static String CustomDateFormat(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date newDate = null;
        try {
            newDate = format.parse(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        format = new SimpleDateFormat("dd 'de' MMMM, yyyy");
        String formatedDate = format.format(newDate);

        return formatedDate;
    }
}
