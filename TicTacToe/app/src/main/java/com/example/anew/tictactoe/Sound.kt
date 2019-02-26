package com.example.anew.tictactoe

import android.app.Service
import android.content.Intent
import android.content.res.Resources
import android.media.MediaPlayer
import android.os.IBinder

class Sound: Service() {
    var player: MediaPlayer?=null
    override fun onBind(intent: Intent?): IBinder? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate() {
       super.onCreate()
        player = MediaPlayer.create(this, R.raw.sound)
        player?.isLooping = true

    }
    override fun onDestroy() {
       super.onDestroy()
        player?.stop()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        player?.start()
        return super.onStartCommand(intent, flags, startId)
    }

}


