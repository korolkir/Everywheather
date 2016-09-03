package com.example.korolkir.everywheatherdemo.model;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.example.korolkir.everywheatherdemo.R;
import com.example.korolkir.everywheatherdemo.view.MainActivity;

/**
 * Created by korolkir on 10.08.16.
 */
public class NotificationSender {

    private NotificationManager notificationManager;
    private Context context;


    public NotificationSender(Context context) {
        this.context = context;
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public void sendNotification() {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.drizzle_day)
                        .setContentTitle(context.getResources().getString(R.string.rainy_notification_content_title))
                        .setContentText(context.getResources().getString(R.string.rainy_notification_content_text));
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        mBuilder.setSound(alarmSound);
        Intent resultIntent = new Intent(context, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        notificationManager.notify(123, mBuilder.build());
    }
}
