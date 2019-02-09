package khoroshkov.androidproject

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import khoroshkov.androidproject.ui.MainActivityUI
import org.jetbrains.anko.*
import com.google.android.exoplayer2.util.Log
import com.google.android.exoplayer2.Player
import org.jetbrains.anko.sdk27.coroutines.onClick
import android.widget.SeekBar
import khoroshkov.androidproject.player.PlayerService
import khoroshkov.androidproject.utils.*
import android.content.ComponentName
import android.content.Context
import android.os.IBinder
import android.content.ServiceConnection
import com.google.android.exoplayer2.ExoPlaybackException

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(), Player.EventListener {
    private val handler = Handler()
    private var playerService: PlayerService? = null
    private val playerConnection = object : ServiceConnection {

        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            val binder = service as PlayerService.PlayerBinder
            playerService = binder.service
            MainActivityUI.PLAYER_VIEW.player = playerService?.player
            playerService?.player?.addListener(this@MainActivity)
            val player = playerService?.player
            if (player != null && player.playWhenReady && player.playbackState == Player.STATE_READY) {
                toPauseButton()
                initTrackInfo()
                startUpdatingProgressBar()
            } else {
                toPlayButton()
            }
            if (player?.shuffleModeEnabled == true) {
                toShuffleButton()
            } else {
                toShuffleInactiveButton()
            }
            when (player?.repeatMode) {
                Player.REPEAT_MODE_ONE -> toRepeatOneButton()
                Player.REPEAT_MODE_ALL -> toRepeatAllButton()
                else -> {
                    toRepeatInactiveButton()
                }
            }
            MainActivityUI.PREVIOUS_BUTTON.onClick { playerService?.player?.previous() }
            MainActivityUI.NEXT_BUTTON.onClick { playerService?.player?.next() }
            MainActivityUI.LIST_BUTTON.onClick { startActivity<PlaylistActivity>() }
            MainActivityUI.SEEK_BAR.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {}

                override fun onStartTrackingTouch(seekBar: SeekBar) {
                    stopUpdatingProgressBar()
                }

                override fun onStopTrackingTouch(seekBar: SeekBar) {
                    val currentPosition = MainActivityUI.SEEK_BAR.progress.toLong()
                    playerService?.player?.seekTo(currentPosition)
                    startUpdatingProgressBar()
                }
            })
        }

        override fun onServiceDisconnected(name: ComponentName) {
            playerService = null
            playerService?.player?.removeListener(this@MainActivity)
            MainActivityUI.PLAYER_VIEW.player = null
            stopUpdatingProgressBar()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate()")
        MainActivityUI().setContentView(this)
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart()")
        val playerIntent = Intent(this, PlayerService::class.java)
        Log.i(TAG, "bindService")
        bindService(playerIntent, playerConnection, Context.BIND_AUTO_CREATE)
        Log.i(TAG, "startService")
        startService(playerIntent)
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop()")
        unbindService(playerConnection)
    }

    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        if (playWhenReady && playbackState == Player.STATE_READY) {
            Log.i(TAG, "Audio is playing")
            initTrackInfo()
            toPauseButton()
            startUpdatingProgressBar()
        } else {
            Log.i(TAG, "Audio is paused")
            toPlayButton()
        }
    }

    override fun onRepeatModeChanged(repeatMode: Int) {
        when (repeatMode) {
            Player.REPEAT_MODE_OFF -> toRepeatInactiveButton()
            Player.REPEAT_MODE_ONE -> toRepeatOneButton()
            Player.REPEAT_MODE_ALL -> toRepeatAllButton()
        }
    }

    override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {
        if (shuffleModeEnabled) {
            toShuffleButton()
        } else {
            toShuffleInactiveButton()
        }
    }

    override fun onPlayerError(error: ExoPlaybackException?) {
        Log.e(TAG, error.toString())
    }

    private fun initTrackInfo() {
        Log.i(TAG, "initTrackInfo()")
        val player = playerService?.player
        if (player != null) {
            val duration = player.duration
            Log.i(TAG, "Duration: $duration")
            MainActivityUI.SEEK_BAR.max = duration.toInt()
            MainActivityUI.DURATION.text = duration.toTime()
            val currentSong = playerService?.currentSong
            if (currentSong != null) {
                MainActivityUI.ARTIST.text = currentSong.artist
                MainActivityUI.TITLE.text = currentSong.title
            } else {
                MainActivityUI.ARTIST.text = ARTIST_DEFAULT
                MainActivityUI.TITLE.text = TITLE_DEFAULT
            }
        }
    }

    private fun toPauseButton() {
        Log.i(TAG, "toPauseButton()")
        MainActivityUI.PLAY_BUTTON.imageResource = R.drawable.ic_pause
        MainActivityUI.PLAY_BUTTON.onClick {
            Log.i(TAG, "Pause button clicked")
            playerService?.player?.playWhenReady = false
        }
    }

    private fun toPlayButton() {
        Log.i(TAG, "toPlayButton()")
        MainActivityUI.PLAY_BUTTON.imageResource = R.drawable.ic_play
        MainActivityUI.PLAY_BUTTON.onClick {
            Log.i(TAG, "Play button clicked")
            val playlist = playerService?.playlist
            if (playlist != null && !playlist.isEmpty()) {
                playerService?.player?.playWhenReady = true
            } else {
                startActivity<PlaylistActivity>()
            }
        }
    }

    private fun toShuffleButton() {
        Log.i(TAG, "toShuffleButton()")
        MainActivityUI.SHUFFLE_BUTTON.onClick {
            playerService?.player?.shuffleModeEnabled = false
        }
        MainActivityUI.SHUFFLE_BUTTON.imageResource = R.drawable.ic_shuffle
    }

    private fun toShuffleInactiveButton() {
        Log.i(TAG, "toShuffleInactiveButton()")
        MainActivityUI.SHUFFLE_BUTTON.onClick {
            playerService?.player?.shuffleModeEnabled = true
        }
        MainActivityUI.SHUFFLE_BUTTON.imageResource = R.drawable.ic_shuffle_off
    }

    private fun toRepeatAllButton() {
        Log.i(TAG, "toRepeatAllButton()")
        MainActivityUI.REPEAT_BUTTON.onClick {
            playerService?.player?.repeatMode = Player.REPEAT_MODE_OFF
        }
        MainActivityUI.REPEAT_BUTTON.imageResource = R.drawable.ic_repeat_all
    }

    private fun toRepeatOneButton() {
        Log.i(TAG, "toRepeatOneButton()")
        MainActivityUI.REPEAT_BUTTON.onClick {
            playerService?.player?.repeatMode = Player.REPEAT_MODE_ALL
        }
        MainActivityUI.REPEAT_BUTTON.imageResource = R.drawable.ic_repeat_one
    }

    private fun toRepeatInactiveButton() {
        Log.i(TAG, "toRepeatInactiveButton()")
        MainActivityUI.REPEAT_BUTTON.onClick {
            playerService?.player?.repeatMode = Player.REPEAT_MODE_ONE
        }
        MainActivityUI.REPEAT_BUTTON.imageResource = R.drawable.ic_repeat_off
    }

    private fun startUpdatingProgressBar() {
        handler.postDelayed(updateTimeTask, 100)
    }

    private fun stopUpdatingProgressBar() {
        handler.removeCallbacks(updateTimeTask)
    }

    private val updateTimeTask = object : Runnable {
        override fun run() {
            val progress = playerService?.player?.currentPosition
            if (progress != null) {
                MainActivityUI.TIME_POSITION.text = progress.toTime()
                MainActivityUI.SEEK_BAR.progress = progress.toInt()
            }
            handler.postDelayed(this, 100)
        }
    }
}