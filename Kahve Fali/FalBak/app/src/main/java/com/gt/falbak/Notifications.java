package com.gt.falbak;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.gt.falbak.ui.kahvefali.KahveSonucu;
import com.gt.falbak.ui.kahvefali.Waiting;

import static android.content.Context.NOTIFICATION_SERVICE;

public class Notifications extends AppCompatActivity {
    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    public static final String NOTIFICATION_CHANNEL_NAME = "NOTIFICATION_CHANNEL_NAME" ;
    private final static String default_notification_channel_id = "default" ;

    NotificationManager mNotificationManager ;
    int notificationId = 0 ;

//    NotificationManager notificationManager;
    private Context context;

    public Notifications(Context context, NotificationManager nManager) {
        this.context = context;
        mNotificationManager =  nManager;
//        notificationManager =  nManager;
    }
//
//    public void createNotification(int id) {
//        Intent intent = new Intent(context, KahveSonucu.class);
//        PendingIntent pIntent = PendingIntent.getActivity(context, (int) System.currentTimeMillis(), intent, 0);
//
//        Notification noti = new Notification.Builder(context)
//                .setOnlyAlertOnce(true)
//                .setContentTitle("Falınız Hazır.")
//                .setContentText("Merakla Beklediğiniz An Geldi :)")
//                .setSmallIcon(R.drawable.cofee_cup_h)
//                .setContentIntent(pIntent)
//                .setDeleteIntent(pIntent)
//                .addAction(R.drawable.ic_send, "Falını Oku", pIntent).build();
//
//        noti.flags |= Notification.FLAG_AUTO_CANCEL;
//        noti.flags |= Notification.DEFAULT_VIBRATE;
//        noti.flags |= Notification.DEFAULT_SOUND;
//
//        notificationManager.notify(id, noti);
//    }
//
//    public void removeNotification(int id) {
//        notificationManager.cancel(1);
//        notificationManager.cancelAll();
//
//    }
//
    public void createNotification() {
        long[] vibrate = {0, 200, 400};

        RemoteViews contentView = new RemoteViews(context.getPackageName() , R.layout.activity_notification_receiver);

        Intent intent = new Intent(context, Waiting.class);
        Bundle bindent = new Bundle();
        bindent.putInt("waiting", 0);
        intent.putExtras(bindent);

        PendingIntent pIntent = PendingIntent.getActivity(context, (int) System.currentTimeMillis(), intent, 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, default_notification_channel_id);
        mBuilder.setContent(contentView);
        mBuilder.setSmallIcon(R.drawable.cofee_cup_h);
        mBuilder.setContentIntent(pIntent);
        mBuilder.setAutoCancel(true);
        mBuilder.setColorized(true);
        mBuilder.setOngoing(true);
        mBuilder.setOnlyAlertOnce(true);
        mBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        mBuilder.setDefaults(NotificationCompat.DEFAULT_ALL);
        mBuilder.setLights(
                0xffffff00,1,0);
        mBuilder.setVibrate(vibrate);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel =
                    new NotificationChannel(NOTIFICATION_CHANNEL_ID , NOTIFICATION_CHANNEL_NAME , importance);
            notificationChannel.enableLights(true);
            notificationChannel.shouldShowLights();

            mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
            assert mNotificationManager != null;
            mNotificationManager.createNotificationChannel(notificationChannel);
        }

        Notification notification = mBuilder.build();
        notification.flags = Notification.FLAG_SHOW_LIGHTS;
        notification.ledARGB = 0xffffff00;
        notification.ledOnMS = 1;
        notification.ledOffMS = 0;

        notificationId = (int) System.currentTimeMillis();
        assert mNotificationManager != null;
        mNotificationManager.notify(notificationId , notification);
    }
    public void removeNotification() {
        if (notificationId != 0)
            mNotificationManager.cancel(notificationId);
    }
}
