package khoroshkov.androidproject.player

import android.app.Service
import android.content.ContentUris
import android.content.Intent
import android.os.IBinder
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import khoroshkov.androidproject.utils.Song
import android.os.Binder
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.util.Log
import khoroshkov.androidproject.R
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter

private const val TAG = "PlayerService"

class PlayerService : Service() {
    private lateinit var exoPlayer: ExoPlayer
    val player: ExoPlayer
        get() = exoPlayer
    private val playerBinder = PlayerBinder()
    var playlist: List<Song> = listOf()
        set(value) {
            Log.i(TAG, "Set playlist")
            field = value
            val listOfURI = playlist.map {
                ContentUris.withAppendedId(android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, it.id)
            }
            val bandwidthMeter = DefaultBandwidthMeter()
            val dataSourceFactory =
                DefaultDataSourceFactory(
                    this,
                    Util.getUserAgent(this, resources.getString(R.string.app_name)),
                    bandwidthMeter
                )
            val extractorsFactory = DefaultExtractorsFactory()
            val mediaSources = listOfURI.map {
                ExtractorMediaSource(it, dataSourceFactory, extractorsFactory, null, null)
            }
            val concatenatedMediaSource = ConcatenatingMediaSource()
            for (mediaSource in mediaSources) {
                concatenatedMediaSource.addMediaSource(mediaSource)
            }
            Log.i(TAG, "Preparing MediaSource")
            exoPlayer.prepare(concatenatedMediaSource)
            Log.i(TAG, "Prepared MediaSource")
            exoPlayer.playWhenReady = true
        }
    val currentSong: Song?
        get() {
            val index = exoPlayer.currentWindowIndex
            Log.i(TAG, "exoPlayer.currentWindowIndex = $index")
            Log.i(TAG, "playlist.size = ${playlist.size}")
            return if (playlist.size > index) {
                playlist[index]
            } else {
                Log.e(TAG, "exoPlayer.currentWindowIndex >= playlist.size")
                null
            }
        }

    override fun onCreate() {
        super.onCreate()
        exoPlayer = ExoPlayerFactory.newSimpleInstance(this, DefaultTrackSelector())
    }

    override fun onBind(intent: Intent?): IBinder {
        Log.i(TAG, "Bound")
        return playerBinder
    }

    override fun onUnbind(intent: Intent): Boolean {
        Log.i(TAG, "Unbound")
        exoPlayer.stop()
        exoPlayer.release()
        return false
    }

    inner class PlayerBinder : Binder() {
        internal val service: PlayerService
            get() = this@PlayerService
    }
}