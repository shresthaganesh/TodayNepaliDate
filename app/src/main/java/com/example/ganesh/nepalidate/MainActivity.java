package com.example.ganesh.nepalidate;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RemoteViews;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int colorIndex = 0;
    Spinner colorsList;
    Button update, close;
    TextView todaysDate, textcolor;
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

        //Toast.makeText(MainActivity.this, Utils.getTextViewString(), Toast.LENGTH_LONG).show();
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
