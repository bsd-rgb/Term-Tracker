package com.zybooks.bdavis_wguscheduler.UI;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.zybooks.bdavis_wguscheduler.R;

public class MyReceiver extends BroadcastReceiver {

    String channel_id = "scheduler";
    static int notificationId;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("MyReceiver", "Received broadcast: " + intent.getStringExtra("message"));
        createNotificationChannel(context, channel_id);
        Notification notification = new NotificationCompat.Builder(context, channel_id)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentText(intent.getStringExtra("message"))
                .setContentTitle("Scheduler Notification").build();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationId++, notification);
        Log.d("MyReceiver", "Notification sent: " + intent.getStringExtra("message"));

    }

    private void createNotificationChannel(Context context, String CHANNEL_ID){
        CharSequence name = "channelname";
        String description = "channeldescription";
        int importance = NotificationManager.IMPORTANCE_HIGH;

        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription(description);
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
        Log.d("MyReceiver", "Notification channel created: " + CHANNEL_ID);
    }
}