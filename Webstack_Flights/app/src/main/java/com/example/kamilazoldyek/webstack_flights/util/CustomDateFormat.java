package com.example.kamilazoldyek.webstack_flights.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomDateFormat {

    public static String CustomDateFormatA(String date) {
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
    public static String CustomDateFormatDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        Date newDate = null;
        try {
            newDate = format.parse(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        format = new SimpleDateFormat("EE, dd/MM");
        String formatedDate = format.format(newDate);

        return formatedDate;
    }

    public static String CustomDateFormatTime(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        Date newDate = null;
        try {
            newDate = format.parse(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        format = new SimpleDateFormat("HH:mm");
        String formatedDate = format.format(newDate);

        return formatedDate;
    }

}
