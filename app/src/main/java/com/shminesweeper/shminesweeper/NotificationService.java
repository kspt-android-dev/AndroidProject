package com.shminesweeper.shminesweeper;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class NotificationService extends Service {

    // период уведомления 6 часов
    public static final int PERIOD = 1000*60*60*6;  // 1000 мс = 1 с
    Timer timer;

    @Override
    public void onCreate() {
        super.onCreate();

        Log.i("service", "statred");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        timer = new Timer();
        timer.schedule(new ShowNotification(), PERIOD, PERIOD);

        return super.onStartCommand(intent, flags, startId);
    }

    class ShowNotification extends TimerTask {

        @Override
        public void run() {
            Log.i("Notification", "sent");
            setNotification();
        }
    }

    private void setNotification() {

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getResources().getText(R.string.notification_title))
                .setContentText(getResources().getText(R.string.notification_text))
                .setAutoCancel(true);

        Intent resultIntent = new Intent(this, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                0,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1,mBuilder.build());

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
        Log.i("service", "stopped");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
