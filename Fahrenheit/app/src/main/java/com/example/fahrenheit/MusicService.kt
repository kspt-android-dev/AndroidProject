package com.example.fahrenheit

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.widget.Toast
import androidx.annotation.Nullable


class MusicService : Service() {

    private var mPlayer: MediaPlayer? = null

    @Nullable
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        mPlayer = MediaPlayer.create(this, R.raw.main_theme)
        mPlayer!!.isLooping = false
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {

        mPlayer!!.start()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPlayer!!.stop()
    }
}