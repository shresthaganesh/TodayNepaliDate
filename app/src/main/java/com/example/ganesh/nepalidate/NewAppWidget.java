package com.example.ganesh.nepalidate;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.widget.RemoteViews;
import android.widget.Toast;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {

    static String ACTION_KEY = "NEPALI_WIDGET_CLICKED";


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Intent intent = new Intent(context, NewAppWidget.class);
        intent.setAction(ACTION_KEY);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent, 0);


        CharSequence widgetText = Utils.getTextViewString();
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);

        views.setTextViewText(R.id.appwidget_text, widgetText);
        views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction().equals(ACTION_KEY)) {

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);

            //views.setTextColor(R.id.appwidget_text, Color.RED);


            PendingIntent intent1 = PendingIntent.getActivity(context,0,new Intent(context,MainActivity.class),0);
            views.setOnClickPendingIntent(R.id.appwidget_text,intent1);

            ComponentName name = new ComponentName(context, NewAppWidget.class);
            int[] ids = AppWidgetManager.getInstance(context).getAppWidgetIds(name);


            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            // Instruct the widget manager to update the widget
            for (int i : ids) {
                //Toast.makeText(context, "Currently "+views.getLayoutId()+" Clicked:" + String.valueOf(i), Toast.LENGTH_SHORT).show();
                appWidgetManager.updateAppWidget(i, views);
            }
        }
    }
}

