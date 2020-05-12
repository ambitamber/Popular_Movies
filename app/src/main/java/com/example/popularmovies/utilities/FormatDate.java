package com.example.popularmovies.utilities;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatDate {
    public static String dateTime(String movieDate) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat inSDF = new SimpleDateFormat("yyyy-mm-dd");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat outSDF = new SimpleDateFormat("mm/dd/yyyy");
        String outDate = "";
        if (movieDate != null) {
            try {
                Date date = inSDF.parse(movieDate);
                assert date != null;
                outDate = outSDF.format(date);
            } catch (ParseException e){
                e.printStackTrace();
            }
        }
        return outDate;
    }
}
