package com.bivinvinod.bivin.notificaktu;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

public class MyReceiver extends BroadcastReceiver {


    public MyReceiver() {
    }
    @Override
    public void onReceive(Context context, Intent intent) {

        final String CHANNEL_ID ="Nofica" ;
        int mReceivedID = Integer.parseInt(intent.getStringExtra("id"));
        ReminderDb reminderDb=new ReminderDb(context);
        Reminder reminder=reminderDb.getReminder(mReceivedID);
        reminderDb.turnOffReminder(mReceivedID);
        String mTitle=reminder.getTitle();
        int SUMMARY_ID = 0;
        CharSequence groupName ="Notifica Notification";
        int notifyID = 1;
        String GROUP_KEY_MYNOTI = "notifica";
        CharSequence name ="Notifica" ;
        int importance = NotificationManager.IMPORTANCE_HIGH;

//***************************************************************************************************************************
        Intent intent1 = new Intent(context, MainActivity.class);
        PendingIntent pendIntent = PendingIntent.getActivity(context, 0, intent1, 0);
//***************************************************************************************************************************

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.setDescription("Notifica");
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.CYAN);
            mChannel.enableVibration(true);
            mNotificationManager.createNotificationChannel(mChannel);
            NotificationChannelGroup mGroup = new NotificationChannelGroup(GROUP_KEY_MYNOTI, groupName);
            mNotificationManager.createNotificationChannelGroup(mGroup);
            // Create a notification and set the notification channel.

            Notification notification = new NotificationCompat.Builder(context,CHANNEL_ID)
                    .setContentTitle("Notifca")
                    .setContentText(mTitle)
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher_background))
                    .setChannelId(CHANNEL_ID)
                    .setOnlyAlertOnce(true)
                    .setAutoCancel(true)
                    .setAutoCancel(true)
                    .setGroup(GROUP_KEY_MYNOTI)
                    .setGroupSummary(true)
                    .setColor(context.getResources().getColor(R.color.colorPrimary))
                    .addAction(R.drawable.ic_launcher,"Turn Off",pendIntent)
                    .build();
            mNotificationManager.notify(mReceivedID ,notification);
        }
        else {
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context,CHANNEL_ID)
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setContentTitle(context.getResources().getString(R.string.app_name))
                    .setTicker(mTitle)
                    .setContentText(mTitle)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    //.setContentIntent(mClick)
                    .setAutoCancel(true)
                    .setOnlyAlertOnce(true)
                    .addAction(R.drawable.ic_launcher,"Turn Off",pendIntent);

            NotificationManager nManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            nManager.notify(mReceivedID, mBuilder.build());
        }

    }
}
