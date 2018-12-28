package com.example.checkers;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

public class MusicService extends Service {

    MediaPlayer player;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("ServiceMusic", "Start");
        player = MediaPlayer.create(this, R.raw.fon);
        player.setLooping(true);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        player.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        player.stop();
    }


}
