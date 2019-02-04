package khoroshkov.androidproject

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
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
import java.util.concurrent.TimeUnit
import com.google.android.exoplayer2.Player
import org.jetbrains.anko.sdk27.coroutines.onClick

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private var player: SimpleExoPlayer by Delegates.notNull()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainActivityUI().setContentView(this)
    }

    override fun onStart() {
        super.onStart()
        player = ExoPlayerFactory.newSimpleInstance(this, DefaultTrackSelector())
        MainActivityUI.PLAYER_VIEW.player = player
        val dataSourceFactory = DefaultDataSourceFactory(this,
            Util.getUserAgent(this, resources.getString(R.string.app_name)))
        val mediaSource = ExtractorMediaSource.Factory(dataSourceFactory)
            .createMediaSource(RawResourceDataSource.buildRawResourceUri(R.raw.zombie))
        player.prepare(mediaSource)
        player.playWhenReady = true
        player.addListener(object : Player.EventListener {
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                if (playWhenReady && playbackState == Player.STATE_READY) {
                    Log.i(TAG, "Audio is playing")
                    initTrackInfo()
                    toPauseButton()
                } else if (playWhenReady) {
                    // might be idle (plays after prepare()),
                    // buffering (plays when data available)
                    // or ended (plays when seek away from end)
                } else {
                    Log.i(TAG, "Audio is paused")
                    toPlayButton()
                }
            }
        })
    }

    override fun onStop() {
        super.onStop()
        //playerView.player = null
        player.release()
        // player = null // in the video from IO18 but I use Delegates.NotNull
    }

    fun initTrackInfo() {
        val duration = player.duration
        Log.i(TAG, "Duration: $duration")
        MainActivityUI.SEEK_BAR.max = duration.toInt()
        MainActivityUI.DURATION.text = duration.toTime()
        MainActivityUI.ARTIST.text
        MainActivityUI.TRACK.text
    }

    fun toPauseButton() {
        Log.i(TAG, "toPauseButton()")
        MainActivityUI.PLAY_BUTTON.imageResource = R.drawable.ic_pause
        MainActivityUI.PLAY_BUTTON.onClick {
            Log.i(TAG, "Pause button clicked")
            player.playWhenReady = false
        }
    }

    fun toPlayButton() {
        Log.i(TAG, "toPlayButton()")
        MainActivityUI.PLAY_BUTTON.imageResource = R.drawable.ic_play
        MainActivityUI.PLAY_BUTTON.onClick {
            Log.i(TAG, "Play button clicked")
            player.playWhenReady = true
        }
    }

    private fun Long.toTime(): String {
        val hours = TimeUnit.MILLISECONDS.toHours(this)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(this) % 60
        val seconds = TimeUnit.MILLISECONDS.toSeconds(this)  % 60
        return if (hours > 0) {
            String.format("%02d:%02d:%02d", hours, minutes, seconds)
        } else {
            String.format("%02d:%02d", minutes, seconds)
        }
    }
}