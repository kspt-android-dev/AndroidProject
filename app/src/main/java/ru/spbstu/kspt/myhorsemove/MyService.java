package ru.spbstu.kspt.myhorsemove;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class MyService extends Service {
    static MediaPlayer player;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        player = MediaPlayer.create(this, R.raw.zero);
        player.setLooping(true); // зацикливаем
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        player.stop();
    }

//    @Override old
//    public void onStart(Intent intent, int startId) {
//        player.start();
//    }


    @Override // new onStart
    public int onStartCommand(Intent intent, int flags, int startId) {
        player.start();
        return super.onStartCommand(intent, flags, startId);
    }
}
