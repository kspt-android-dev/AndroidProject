package khoroshkov.androidproject

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import khoroshkov.androidproject.ui.MainActivityUI
import org.jetbrains.anko.*
import kotlin.properties.Delegates
import com.google.android.exoplayer2.upstream.RawResourceDataSource
import com.google.android.exoplayer2.util.Log
import com.google.android.exoplayer2.Player
import org.jetbrains.anko.sdk27.coroutines.onClick
import android.widget.SeekBar
import khoroshkov.androidproject.utils.*

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private var player: SimpleExoPlayer by Delegates.notNull()
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate()")
        MainActivityUI().setContentView(this)
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart()")
        player = ExoPlayerFactory.newSimpleInstance(this, DefaultTrackSelector())
        MainActivityUI.PLAYER_VIEW.player = player
        val dataSourceFactory = DefaultDataSourceFactory(this, Util.getUserAgent(this, resources.getString(R.string.app_name)))
        val mediaSource = ExtractorMediaSource.Factory(dataSourceFactory)
            .createMediaSource(RawResourceDataSource.buildRawResourceUri(R.raw.zombie))
        toPlayButton()
        toRepeatInactiveButton()
        toShuffleInactiveButton()
        MainActivityUI.PREVIOUS_BUTTON.onClick { player.previous() }
        MainActivityUI.NEXT_BUTTON.onClick { player.next() }
        MainActivityUI.LIST_BUTTON.onClick { startActivity<PlaylistActivity>() }
        player.prepare(mediaSource)
        player.playWhenReady = true
        player.addListener(object : Player.EventListener {
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                if (playWhenReady && playbackState == Player.STATE_READY) {
                    Log.i(TAG, "Audio is playing")
                    initTrackInfo()
                    toPauseButton()
                } else {
                    Log.i(TAG, "Audio is paused")
                    toPlayButton()
                }
            }

            override fun onRepeatModeChanged(repeatMode: Int) {
                when(repeatMode) {
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
        })
        MainActivityUI.SEEK_BAR.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {}

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                handler.removeCallbacks(updateTimeTask)
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                handler.removeCallbacks(updateTimeTask)
                val currentPosition = MainActivityUI.SEEK_BAR.progress.toLong()
                player.seekTo(currentPosition)
                startUpdatingProgressBar()
            }
        })
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop()")
        //playerView.player = null
        player.release()
        // player = null // in the video from IO18 but I use Delegates.NotNull
    }

    private fun initTrackInfo() {
        val duration = player.duration
        Log.i(TAG, "Duration: $duration")
        MainActivityUI.SEEK_BAR.max = duration.toInt()
        MainActivityUI.DURATION.text = duration.toTime()
        startUpdatingProgressBar()
        MainActivityUI.ARTIST.text
        MainActivityUI.TITLE.text
    }

    private fun toPauseButton() {
        Log.i(TAG, "toPauseButton()")
        MainActivityUI.PLAY_BUTTON.imageResource = R.drawable.ic_pause
        MainActivityUI.PLAY_BUTTON.onClick {
            Log.i(TAG, "Pause button clicked")
            player.playWhenReady = false
        }
    }

    private fun toPlayButton() {
        Log.i(TAG, "toPlayButton()")
        MainActivityUI.PLAY_BUTTON.imageResource = R.drawable.ic_play
        MainActivityUI.PLAY_BUTTON.onClick {
            Log.i(TAG, "Play button clicked")
            player.playWhenReady = true
        }
    }

    private fun toShuffleButton() {
        Log.i(TAG, "toShuffleButton()")
        MainActivityUI.SHUFFLE_BUTTON.onClick {
            player.shuffleModeEnabled = false
        }
        MainActivityUI.SHUFFLE_BUTTON.imageResource = R.drawable.ic_shuffle
    }

    private fun toShuffleInactiveButton() {
        Log.i(TAG, "toShuffleInactiveButton()")
        MainActivityUI.SHUFFLE_BUTTON.onClick {
            player.shuffleModeEnabled = true
        }
        MainActivityUI.SHUFFLE_BUTTON.imageResource = R.drawable.ic_shuffle_off
    }

    private fun toRepeatAllButton() {
        Log.i(TAG, "toRepeatAllButton()")
        MainActivityUI.REPEAT_BUTTON.onClick {
            player.repeatMode = Player.REPEAT_MODE_OFF
        }
        MainActivityUI.REPEAT_BUTTON.imageResource = R.drawable.ic_repeat_all
    }

    private fun toRepeatOneButton() {
        Log.i(TAG, "toRepeatOneButton()")
        MainActivityUI.REPEAT_BUTTON.onClick {
            player.repeatMode = Player.REPEAT_MODE_ALL
        }
        MainActivityUI.REPEAT_BUTTON.imageResource = R.drawable.ic_repeat_one
    }

    private fun toRepeatInactiveButton() {
        Log.i(TAG, "toRepeatInactiveButton()")
        MainActivityUI.REPEAT_BUTTON.onClick {
            player.repeatMode = Player.REPEAT_MODE_ONE
        }
        MainActivityUI.REPEAT_BUTTON.imageResource = R.drawable.ic_repeat_off
    }

    private fun startUpdatingProgressBar() {
        handler.postDelayed(updateTimeTask, 100)
    }

    private val updateTimeTask = object : Runnable {
        override fun run() {
            val progress = player.currentPosition
            MainActivityUI.TIME_POSITION.text = progress.toTime()
            MainActivityUI.SEEK_BAR.progress = progress.toInt()
            handler.postDelayed(this, 100)
        }
    }
}