package com.example.ganesh.nepalidate;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.graphics.Color;
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
import java.util.List;

public class MainActivity extends AppCompatActivity {

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
        //Toast.makeText(MainActivity.this, Utils.getTextViewString(), Toast.LENGTH_LONG).show();

        //UpdateMonths();
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
