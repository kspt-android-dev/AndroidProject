package khoroshkov.androidproject

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import khoroshkov.androidproject.ui.MainActivityUI
import org.jetbrains.anko.*
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {

    private var playerView: PlayerView by Delegates.notNull()
    private var player: SimpleExoPlayer by Delegates.notNull()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainActivityUI().setContentView(this)
        playerView = MainActivityUI.PLAYER_VIEW
    }

    override fun onStart() {
        super.onStart()
        player = ExoPlayerFactory.newSimpleInstance(this, DefaultTrackSelector())
        playerView.player = player
        val dataSourceFactory = DefaultDataSourceFactory(this,
            Util.getUserAgent(this, resources.getString(R.string.app_name)))
        val mediaSource = ExtractorMediaSource.Factory(dataSourceFactory)
            .createMediaSource(Uri.parse(""))
        player.prepare(mediaSource)
        player.playWhenReady = true
    }

    override fun onStop() {
        super.onStop()
        //playerView.player = null
        player.release()
        // player = null // in the video from IO18 but I use Delegates.NotNull
    }
}