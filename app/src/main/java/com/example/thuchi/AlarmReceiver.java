package com.example.thuchi;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int notificationId = intent.getIntExtra("notificationId",0);
        String message = intent.getStringExtra("todo");

        Intent mainIntent = new Intent(context,ThuChiActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context,0,mainIntent,0);

        NotificationManager myNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(context);
        builder.setSmallIcon(R.drawable.ic_one)
                .setContentTitle("Thông báo")
                .setContentText(message)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setContentIntent(contentIntent);

        myNotificationManager.notify(notificationId, builder.build());

    }
}
