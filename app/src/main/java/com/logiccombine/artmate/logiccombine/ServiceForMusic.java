package com.logiccombine.artmate.logiccombine;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
public class ServiceForMusic extends Service {
    private static final String TAG = "ServiceForMusic";
    MediaPlayer player;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        player = MediaPlayer.create(this, R.raw.backgroundmusic);
        player.setLooping(true); // зацикливаем
    }

    @Override
    public void onDestroy() {
        player.stop();
    }

    @Override
    public void onStart(Intent intent, int startid) {
        player.start();
    }
}