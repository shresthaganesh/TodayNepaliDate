package com.example.ganesh.nepalidate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ganesh shrestha on 3/14/2018.
 */

public class Utils {
    static String returnValue = "आजको नेपाली डेट";
    static String[] englishmonths = new String[]{
            "January",
            "Feburary",
            "March",
            "April",
            "May",
            "June",
            "July",
            "August",
            "September",
            "October",
            "November",
            "December"
    };
    static String[] nepalimonths = new String[]{
            "बैशाख",
            "जेष्ठ",
            "आषाढ",
            "श्रावन",
            "भाद्र",
            "असोज",
            "कार्तिक",
            "मंसिर",
            "पौष",
            "माघ",
            "फागुन",
            "चैत्र"
    };
    static HashMap<Integer, String> charactermapping = new HashMap<Integer, String>() {
        {
            put(0, "०");
            put(1, "१");
            put(2, "२");
            put(3, "३");
            put(4, "४");
            put(5, "५");
            put(6, "६");
            put(7, "७");
            put(8, "८");
            put(9, "९ ");
        }

    };

    static HashMap<String, String> daysMapping = new HashMap<String, String>() {
        {
            put("Sunday", "आइतवार");
            put("Monday", "सोमवार");
            put("Tuesday", "मंगलवार");
            put("Wednesday", "बुधवार");
            put("Thursday", "बिहीवार");
            put("Friday", "शुक्रवार");
            put("Saturday", "शनिवार");
        }
    };
    static List<String> englishDaysList = new ArrayList<String>() {
        {
            add("Sunday");
            add("Monday");
            add("Tuesday");
            add("Wednesday");
            add("Thursday");
            add("Friday");
            add("Saturday");
        }
    };

    static String[] englishDays = new String[]{"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

    public static String getTextViewString() {
        java.util.Calendar currentdate = java.util.Calendar.getInstance();
        return GetNepaliDate(currentdate.get(Calendar.YEAR), currentdate.get(Calendar.MONTH) + 1, currentdate.get(Calendar.DAY_OF_MONTH));
    }

    public static String GetEnglishDate(int year, int month, int day) {
        String convertedString = "";
        NepaliDateGenerator.NepDate date = new NepaliDateGenerator().ConvertNepaliDate(year, month + 1, day);

        return convertedString = String.format("%d, %s %d %s", date.Year, englishmonths[date.Month - 1], date.Day, englishDaysList.get(date.WeekDay - 1));
    }

    public static String GetNepaliDate(int year, int month, int day) {
        String nepdate = "";
        NepaliDateGenerator.NepDate date = new NepaliDateGenerator().ConvertEnglishDate(year, month, day);


        String syear = "";
        String smonth = "";
        String sday = "";

//        java.util.Calendar currentdate = java.util.Calendar.getInstance();
//        int todaysyear = currentdate.get(Calendar.YEAR);
//        int todaysmonth = currentdate.get(Calendar.MONTH);
//        int todaysday = currentdate.get(Calendar.DAY_OF_MONTH);


        int todaysyear = date.Year;
        int todaysmonth = date.Month;
        int todaysday = date.Day;

        for (char c : String.valueOf(todaysyear).toCharArray()) {
            int letter = Integer.parseInt(String.valueOf(c));
            syear += charactermapping.get(letter);
        }

        smonth = nepalimonths[todaysmonth - 1];

        for (char c : String.valueOf(todaysday).toCharArray()) {
            int letter = Integer.parseInt(String.valueOf(c));
            sday += charactermapping.get(letter);
        }

        String dayofweek = daysMapping.get(date.WeekDayName);
        //String dayofweek = date.WeekDayName;
        nepdate = String.format("%s, %s %s गते %s", syear, smonth, sday, dayofweek);
        return nepdate;

    }
}
