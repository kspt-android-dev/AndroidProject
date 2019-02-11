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
import khoroshkov.androidproject.data.Song
import android.os.Binder
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.util.Log
import khoroshkov.androidproject.R
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import java.lang.IllegalStateException

private const val TAG = "PlayerService"

class PlayerService : Service() {

    private companion object {
        private var staticPlayer: ExoPlayer? = null
            set(value) {
                if (field != null) {
                    throw IllegalStateException("The field is already initialized")
                } else {
                    field = value
                }
            }
        private var staticPlaylist: List<Song> = listOf()
    }

    val player
        get() = staticPlayer
    private val playerBinder = PlayerBinder()
    var playlist
        get() = staticPlaylist
        set(value) {
            Log.i(TAG, "Set playlist")
            staticPlaylist = value
            val listOfURI = value.map {
                ContentUris.withAppendedId(android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, it.id)
            }
            val bandwidthMeter = DefaultBandwidthMeter()
            val dataSourceFactory =
                DefaultDataSourceFactory(
                    this,
                    Util.getUserAgent(this, resources.getString(R.string.app_name)),
                    bandwidthMeter
                )
            val mediaSourcesFactory = ExtractorMediaSource.Factory(dataSourceFactory)
            val mediaSources = listOfURI.map {
                mediaSourcesFactory.createMediaSource(it)
            }
            val concatenatedMediaSource = ConcatenatingMediaSource()
            for (mediaSource in mediaSources) {
                concatenatedMediaSource.addMediaSource(mediaSource)
            }
            Log.i(TAG, "Preparing MediaSource")
            staticPlayer?.prepare(concatenatedMediaSource)
            Log.i(TAG, "Prepared MediaSource")
            staticPlayer?.playWhenReady = true
        }
    val currentSong: Song?
        get() {
            val temporaryPlayer = staticPlayer
            Log.i(TAG, "playlist.size = ${staticPlaylist.size}")
            return if (temporaryPlayer != null && staticPlaylist.size > temporaryPlayer.currentWindowIndex) {
                Log.i(TAG, "staticPlayer.currentWindowIndex = ${temporaryPlayer.currentWindowIndex}")
                staticPlaylist[temporaryPlayer.currentWindowIndex]
            } else {
                Log.e(TAG, "staticPlayer.currentWindowIndex >= playlist.size or staticPlayer is null")
                null
            }
        }

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "onCreate()")
        if (staticPlayer == null) {
            staticPlayer = ExoPlayerFactory.newSimpleInstance(this, DefaultTrackSelector())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy()")
    }

    override fun onBind(intent: Intent?): IBinder {
        Log.i(TAG, "Bound")
        return playerBinder
    }

    override fun onUnbind(intent: Intent): Boolean {
        Log.i(TAG, "Unbound")
        return false
    }

    inner class PlayerBinder : Binder() {
        val service: PlayerService
            get() = this@PlayerService
    }
}