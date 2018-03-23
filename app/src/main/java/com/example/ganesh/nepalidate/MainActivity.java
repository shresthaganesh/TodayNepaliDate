package com.example.ganesh.nepalidate;


import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.RemoteInput;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RemoteViews;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    int todaysNepday = 1;
    String Notification_Key = "MY_NOTIFICATION_KEY";
    ArrayAdapter<CharSequence> adapter;
    int colorIndex = 0;
    int conversionIndex = 0;
    int monthIndex = 0;
    Spinner colorsList, conversionMonthstype, monthsname;
    Button update, close, convertdate;
    TextView todaysDate, textcolor, year, day;
    int[] availableColors = new int[]
            {
                    Color.BLACK,
                    Color.DKGRAY,
                    Color.GRAY,
                    Color.LTGRAY,
                    Color.WHITE,
                    Color.RED,
                    Color.GREEN,
                    Color.BLUE,
                    Color.YELLOW,
                    Color.CYAN,
                    Color.MAGENTA,
                    Color.TRANSPARENT,
            };
    List<String> englishMonthsList = new ArrayList<String>() {
        {
            add("Jan");
            add("Feb");
            add("Mar");
            add("Apr");
            add("May");
            add("June");
            add("July");
            add("August");
            add("Sept");
            add("Oct");
            add("Nov");
            add("Dec");
        }
    };

    List<String> nepaliMonthsList = new ArrayList<String>() {
        {
            add("बैशाख");
            add("जेठ");
            add("असार");
            add("श्रावण");
            add("भदौ");
            add("आश्विन");
            add("कार्तिक");
            add("मंसिर");
            add("पुष");
            add("माघ");
            add("फाल्गुन");
            add("चैत्र");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        todaysNepday = Utils.getTodaysNepaliDay();
        colorsList = findViewById(R.id.colorslist);
        colorsList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                colorIndex = position;
                textcolor.setTextColor(availableColors[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        update = findViewById(R.id.buttonupdate);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateSettingAndUpdateWidget();
                finish();
            }
        });
        close = findViewById(R.id.buttonclose);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        todaysDate = findViewById(R.id.todaysNepaliDate);
        todaysDate.setText(Utils.getTextViewString());

        textcolor = findViewById(R.id.textviewtextcolor);

        year = findViewById(R.id.widget_year);
        day = findViewById(R.id.widget_day);


        conversionMonthstype = findViewById(R.id.conversionMonthstype);
        conversionMonthstype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                conversionIndex = position;
                UpdateMonths();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        monthsname = findViewById(R.id.Monthsname);
        monthsname.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                monthIndex = position;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        convertdate = findViewById(R.id.widget_convertbutton);
        convertdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                int yearval, monthval, dayval;
                if (year.getText() == "" || day.getText() == "" ||
                        (conversionIndex == 1 && (Integer.parseInt(year.getText().toString()) < 2000 || Integer.parseInt(year.getText().toString()) > 2089)) ||
                        (conversionIndex == 0 && (Integer.parseInt(year.getText().toString()) < 1944 || Integer.parseInt(year.getText().toString()) > 2033))) {
                    dialog.setTitle("Invalid value");
                    dialog.setMessage("Year/Day value is invalid.\nThe Nepali date should range between 2000 B.S to 2089 B.S and\nEnglish should range from 1994 A.D to 2033 A.D")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    dialog.show();
                    return;
                } else

                {
                    yearval = Integer.parseInt(year.getText().toString());
                    monthval = monthsname.getSelectedItemPosition();
                    dayval = Integer.parseInt(day.getText().toString());
                }

                String title = "";
                String message = "";
                NepaliDateGenerator.NepDate converteddate;
                String preDate = String.format("%d/%d/%d", yearval, monthval, dayval);
                String postDate;
                if (conversionIndex == 0)

                {

                    //Eng to Nep
                    title = String.format("Converting '%s'A.D to Nepali(B.S)", preDate);
                    postDate = Utils.GetNepaliDate(yearval, monthval + 1, dayval);
                    message = postDate;
                } else

                {

                    //Nep to Eng
                    title = String.format("Converting '%s'B.s to English(A.D)", preDate);
                    message = Utils.GetEnglishDate(yearval, monthval, dayval);
                }


                dialog.setTitle(title);
                dialog.setMessage(message)
                        .

                                setCancelable(false)
                        .

                                setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                dialog.show();
            }
        });

        //UpdateMonths();

        AssignRepeatAction();
        ShowNotification();
        //UpdateIcon();
    }


    void AssignRepeatAction() {

//        //An example of broadcast
//        Intent intent = new Intent();
//        intent.setFlags(Utils.getTodaysNepaliDay());
//        intent.setAction("com.example.ganesh.nepalidate.DayNotification");
//        intent.putExtra("data", "Notice me senpai!");
//        sendBroadcast(intent);

        long repeatTime = 1 * 5 * 1000;
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, 15);
        calendar.set(Calendar.MINUTE, 39);
        Intent intent = new Intent(getApplicationContext(), DayNotification.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), Utils.NotificationID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), repeatTime, pendingIntent);
    }


    void UpdateSettingAndUpdateWidget() {
        int selectedcolor = availableColors[colorIndex];
        RemoteViews views = new RemoteViews(getPackageName(), R.layout.new_app_widget);

        views.setTextColor(R.id.appwidget_text, selectedcolor);

        ComponentName name = new ComponentName(this, NewAppWidget.class);
        int[] ids = AppWidgetManager.getInstance(this).getAppWidgetIds(name);


        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        // Instruct the widget manager to update the widget
        for (int i : ids) {
            //Toast.makeText(this, "Currently " + views.getLayoutId() + " Clicked:" + String.valueOf(i), Toast.LENGTH_SHORT).show();
            appWidgetManager.updateAppWidget(i, views);
        }


    }

    void UpdateMonths() {
        //monthsname.setAdapter(null);

        if (conversionIndex == 0) {
            //adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, englishMonthsList);
            adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.englishmonthlist,
                    R.layout.support_simple_spinner_dropdown_item);

        } else {
            /* Below code is to generate adapter from list*/
            //adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, nepaliMonthsList);
            /* Below code is to generate adapter from resource*/
            adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.nepalimonthslist,
                    R.layout.support_simple_spinner_dropdown_item);
        }
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthsname.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    void ShowNotification() {

        int graphicID = Utils.allMonthsDays.get(Utils.getTodaysNepaliDay() - 1).getResource();
        NotificationCompat.Builder notificationBuilder = Utils.getNotification(AdditionalFeatures.class, graphicID, this);

        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        manager.notify(Utils.NotificationID, notificationBuilder.build());


//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            // Create the NotificationChannel, but only on API 26+ because
//            // the NotificationChannel class is new and not in the support library
//            CharSequence name = getString(R.string.channel_name);
//            String description = getString(R.string.channel_description);
//            int importance = NotificationManagerCompat.IMPORTANCE_DEFAULT;
//            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
//            channel.setDescription(description);
//            // Register the channel with the system
//            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
//            notificationManager.createNotificationChannel(channel);
//        }

    }

    void UpdateIcon() {
        Bitmap icon = GetBitmap(String.valueOf(todaysDate));

        Intent myLauncherIntent = new Intent(Intent.ACTION_MAIN);
        myLauncherIntent.setClassName(this, this.getClass().getName());
        myLauncherIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        Intent intent = new Intent();
        intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, myLauncherIntent);
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "Application Name");
        intent.putExtra
                (
                        Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
                        Intent.ShortcutIconResource.fromContext
                                (
                                        getApplicationContext(),
                                        R.drawable.a1
                                )
                );
        intent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
        getApplicationContext().sendBroadcast(intent);
    }

    Bitmap GetBitmap(String text) {

        Bitmap bitmap;
        String myText = text;
        Paint paint = new Paint();
        paint.setTextSize(5);
        paint.setColor(Color.GREEN);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setSubpixelText(true);
        paint.setAntiAlias(true);
        float baseline = -paint.ascent();
        int width = (int) (paint.measureText(myText) + 0.5f);
        int height = (int) (baseline + paint.descent() + 0.5f);

        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawText(myText, 0, baseline, paint);
        //Drawable drawable = new BitmapDrawable(getResources(), bitmap);

        return bitmap;
    }

    void NotificationWithInstantReply() {


//        //Bring the numnber entered in the remote view
//        Intent intent = new Intent(Intent.ACTION_DIAL);
//        intent.setData(Uri.parse("tel:0123456789"));
//        // Key for the string that's delivered in the action's intent.
//        final String KEY_TEXT_REPLY = "key_text_reply";
//
//        String replyLabel = "Instant Reply.";// getResources().getString(R.string.reply_label);
//        RemoteInput remoteInput = new RemoteInput.Builder(KEY_TEXT_REPLY)
//                .setLabel(replyLabel)
//                .build();
//
//        // Build a PendingIntent for the reply action to trigger.
//        PendingIntent replyPendingIntent =
//                PendingIntent.getBroadcast(getApplicationContext(),
//                        221,
//                        intent,
//                        PendingIntent.FLAG_UPDATE_CURRENT);
//
//        // Create the reply action and add the remote input.
//        NotificationCompat.Action action =
//                new NotificationCompat.Action.Builder(R.drawable.snooze,
//                        "MyNotification", replyPendingIntent)
//                        .addRemoteInput(remoteInput)
//                        .build();
//
//        // Build the notification and add the action.
//        Notification newMessageNotification = new Notification.Builder(this, Notification_Key)
//                .setSmallIcon(R.drawable.snooze)
//                .setContentTitle("My own notification")
//                .setContentText("Description of my notification.")
//                //.addAction(action)
//                .build();
//
//// Issue the notification.
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
//        notificationManager.notify(569, newMessageNotification);
    }


}

class MyColorMapping {
    public String Name;
    public int ID;

    public MyColorMapping() {
    }

    @Override
    public String toString() {
        return Name;
    }
}


//    MyColorMapping[] colors = new MyColorMapping[12];
//
//    void InitializeColors() {
//        MyColorMapping mapping = new MyColorMapping();
//        mapping.Name = "BLACK";
//        mapping.ID = Color.BLACK;
//
//        mapping = new MyColorMapping();
//        mapping.Name = "DKGRAY";
//        mapping.ID = Color.DKGRAY;
//
//        mapping = new MyColorMapping();
//        mapping.Name = "GRAY";
//        mapping.ID = Color.GRAY;
//
//        mapping = new MyColorMapping();
//        mapping.Name = "LTGRAY";
//        mapping.ID = Color.LTGRAY;
//
//        mapping = new MyColorMapping();
//        mapping.Name = "WHITE";
//        mapping.ID = Color.WHITE;
//
//        mapping = new MyColorMapping();
//        mapping.Name = "RED";
//        mapping.ID = Color.RED;
//
//        mapping = new MyColorMapping();
//        mapping.Name = "GREEN";
//        mapping.ID = Color.GREEN;
//
//        mapping = new MyColorMapping();
//        mapping.Name = "BLUE";
//        mapping.ID = Color.BLUE;
//
//        mapping = new MyColorMapping();
//        mapping.Name = "YELLOW";
//        mapping.ID = Color.YELLOW;
//
//        mapping = new MyColorMapping();
//        mapping.Name = "CYAN";
//        mapping.ID = Color.CYAN;
//
//        mapping = new MyColorMapping();
//        mapping.Name = "MAGENTA";
//        mapping.ID = Color.MAGENTA;
//
//        mapping = new MyColorMapping();
//        mapping.Name = "TRANSPARENT";
//        mapping.ID = Color.TRANSPARENT;
//    }
