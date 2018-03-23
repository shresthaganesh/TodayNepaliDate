package com.example.ganesh.nepalidate;


import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

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
    public static int NotificationID = 7411;
    public static int TodaysNepaliDay = 1;
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
    static List<NumericMapping> allMonthsDays = new ArrayList<NumericMapping>() {
        {
            add(new NumericMapping(1, "१", R.drawable.a1));
            add(new NumericMapping(2, "२", R.drawable.a2));
            add(new NumericMapping(3, "३", R.drawable.a3));
            add(new NumericMapping(4, "४", R.drawable.a4));
            add(new NumericMapping(5, "५", R.drawable.a5));
            add(new NumericMapping(6, "६", R.drawable.a6));
            add(new NumericMapping(7, "७", R.drawable.a7));
            add(new NumericMapping(8, "८", R.drawable.a8));
            add(new NumericMapping(9, "९", R.drawable.a9));
            add(new NumericMapping(10, "१०", R.drawable.a10));
            add(new NumericMapping(11, "११", R.drawable.a11));
            add(new NumericMapping(12, "१२", R.drawable.a12));
            add(new NumericMapping(13, "१३", R.drawable.a13));
            add(new NumericMapping(14, "१४", R.drawable.a14));
            add(new NumericMapping(15, "१५", R.drawable.a15));
            add(new NumericMapping(16, "१६", R.drawable.a16));
            add(new NumericMapping(17, "१७", R.drawable.a17));
            add(new NumericMapping(18, "१८", R.drawable.a18));
            add(new NumericMapping(19, "१९", R.drawable.a19));
            add(new NumericMapping(20, "२०", R.drawable.a20));
            add(new NumericMapping(21, "२१", R.drawable.aa21));
            add(new NumericMapping(22, "२२", R.drawable.a22));
            add(new NumericMapping(23, "२३", R.drawable.a23));
            add(new NumericMapping(24, "२४", R.drawable.a24));
            add(new NumericMapping(25, "२५", R.drawable.a25));
            add(new NumericMapping(26, "२६", R.drawable.a26));
            add(new NumericMapping(27, "२७", R.drawable.a27));
            add(new NumericMapping(28, "२८", R.drawable.a28));
            add(new NumericMapping(29, "२९", R.drawable.a29));
            add(new NumericMapping(30, "३०", R.drawable.a30));
            add(new NumericMapping(31, "३१", R.drawable.a31));
            add(new NumericMapping(32, "३२", R.drawable.a32));


        }
    };
    static String[] englishDays = new String[]{"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

    public static NotificationCompat.Builder getNotification(Class intentType, int iconid, Context context) {
        //Intent that will be shown when notification is touched
        Intent additionalFeatures = new Intent(context, intentType);
        additionalFeatures.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, Utils.NotificationID, additionalFeatures, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mbuilder = new NotificationCompat.Builder(context,Context.NOTIFICATION_SERVICE)
                .setSmallIcon(iconid)
                .setContentTitle("Todays Nepali Date")
                .setContentText(Utils.getTextViewString())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(false)
                .setContentIntent(pendingIntent);

        return mbuilder;
    }

    public static String getTextViewString() {
        java.util.Calendar currentdate = java.util.Calendar.getInstance();
        return GetNepaliDate(currentdate.get(Calendar.YEAR), currentdate.get(Calendar.MONTH) + 1, currentdate.get(Calendar.DAY_OF_MONTH));
    }

    public static int getTodaysNepaliDay() {
        java.util.Calendar currentdate = java.util.Calendar.getInstance();
        int nepday = new NepaliDateGenerator().ConvertEnglishDate(currentdate.get(Calendar.YEAR),
                currentdate.get(Calendar.MONTH) + 1, currentdate.get(Calendar.DAY_OF_MONTH)).Day;
        return nepday;
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
