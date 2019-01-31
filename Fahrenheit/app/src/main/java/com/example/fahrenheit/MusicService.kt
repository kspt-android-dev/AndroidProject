package com.example.fahrenheit

import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import androidx.annotation.Nullable


class MusicService : Service() {

    private var mPlayer: MediaPlayer? = null


    @Nullable
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val extra = intent.extras
        val pref = getSharedPreferences("LIGHT_MOD", Context.MODE_PRIVATE)
        val isMusicale = pref.getBoolean("MUSIC", true)
        if (extra != null) {
            val idMusic = extra.getInt("MUSIC")
            mPlayer = MediaPlayer.create(this, idMusic)
            mPlayer!!.isLooping = false
            if (isMusicale)
                mPlayer!!.start()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        val pref = getSharedPreferences("LIGHT_MOD", Context.MODE_PRIVATE)
        val isMusicable = pref.getBoolean("MUSIC", true)
        if (isMusicable)
            mPlayer!!.stop()
    }
}