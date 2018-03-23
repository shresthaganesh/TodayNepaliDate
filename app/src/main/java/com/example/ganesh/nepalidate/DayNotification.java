package com.example.ganesh.nepalidate;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;

/**
 * Created by ganesh on 3/23/2018.
 */

public class DayNotification extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int graphicID = Utils.allMonthsDays.get(Utils.getTodaysNepaliDay() - 1).getResource();
        NotificationCompat.Builder notificationBuilder = Utils.getNotification(AdditionalFeatures.class, graphicID, context);

        NotificationManagerCompat manager = NotificationManagerCompat.from(context);
        manager.notify(Utils.NotificationID, notificationBuilder.build());
    }
}
