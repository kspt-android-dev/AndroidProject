package khoroshkov.androidproject

import android.Manifest
import android.app.Activity
import android.content.*
import android.os.Bundle
import khoroshkov.androidproject.ui.PlaylistActivityUI
import org.jetbrains.anko.*
import khoroshkov.androidproject.utils.Song
import android.provider.MediaStore
import android.util.Log
import android.view.View
import java.util.*
import android.view.ViewGroup
import android.widget.BaseAdapter
import khoroshkov.androidproject.ui.SongLayout
import android.content.pm.PackageManager
import android.os.IBinder
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.widget.Toast
import org.jetbrains.anko.sdk27.coroutines.onClick
import khoroshkov.androidproject.player.PlayerService

private const val REQUEST_CODE_READ_EXTERNAL_STORAGE = 1
private const val TAG = "PlaylistActivity"

class PlaylistActivity : Activity() {
    private lateinit var songsList: List<Song>
    private var isReadExternalStorageGranted = false
    private var playerService: PlayerService? = null
    private val playerConnection = object : ServiceConnection {

        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            val binder = service as PlayerService.PlayerBinder
            playerService = binder.service
        }

        override fun onServiceDisconnected(name: ComponentName) {
            playerService = null
        }
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate()")
        PlaylistActivityUI().setContentView(this)
    }

    override fun onStart() {
        super.onStart()
        val playerIntent = Intent(this, PlayerService::class.java)
        bindService(playerIntent, playerConnection, Context.BIND_AUTO_CREATE)
        songsList = getSongList()
        Log.i(TAG, "songList.size = " + songsList.size)
        Collections.sort(songsList, object : Comparator<Song> {
            override fun compare(a: Song, b: Song): Int {
                return a.title.compareTo(b.title)
            }
        })
        val songAdapter = SongAdapter(this, songsList)
        PlaylistActivityUI.LIST_VIEW.adapter = songAdapter
        PlaylistActivityUI.PLAY_ALL_BUTTON.onClick {
            playerService?.playlist = songsList
            startActivity<MainActivity>()
        }
    }

    override fun onStop() {
        super.onStop()
        unbindService(playerConnection)
    }

    private fun getSongList(): List<Song> {
        Log.i(TAG, "getSongList()")
        val musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        Log.i(TAG, "musicUri: $musicUri")
        val hasReadExternalStoragePermission =
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        if (hasReadExternalStoragePermission == PackageManager.PERMISSION_GRANTED) {
            isReadExternalStorageGranted = true
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                REQUEST_CODE_READ_EXTERNAL_STORAGE
            )
        }
        val songs = mutableListOf<Song>()
        if (isReadExternalStorageGranted) {
            val musicCursor = contentResolver.query(musicUri, null, null, null, null)
            if (musicCursor != null && musicCursor.moveToFirst()) {
                val titleColumn = musicCursor.getColumnIndex(android.provider.MediaStore.Audio.Media.TITLE)
                val idColumn = musicCursor.getColumnIndex(android.provider.MediaStore.Audio.Media._ID)
                val artistColumn = musicCursor.getColumnIndex(android.provider.MediaStore.Audio.Media.ARTIST)
                do {
                    val thisId = musicCursor.getLong(idColumn)
                    val thisTitle = musicCursor.getString(titleColumn)
                    val thisArtist = musicCursor.getString(artistColumn)
                    songs.add(Song(thisId, thisTitle, thisArtist))
                } while (musicCursor.moveToNext())
            }
            musicCursor?.close()
        }
        return songs
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        Log.i(TAG, "onRequestPermissionsResult()")
        when (requestCode) {
            REQUEST_CODE_READ_EXTERNAL_STORAGE ->
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    isReadExternalStorageGranted = true
                }
        }
        if (!isReadExternalStorageGranted) {
            Toast.makeText(this, "Permission required", Toast.LENGTH_LONG).show()
        }
    }

    inner class SongAdapter(private val context: Context, private val songs: List<Song>) : BaseAdapter() {

        override fun getCount(): Int {
            return songs.size
        }

        override fun getItem(arg0: Int): Any? {
            return null
        }

        override fun getItemId(arg0: Int): Long {
            return 0
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val songLayout = SongLayout(context)
            val titleView = songLayout.title
            val artistView = songLayout.artist
            val song = songs[position]
            titleView.text = song.title
            artistView.text = song.artist
            songLayout.tag = position
            songLayout.onClick {
                playerService?.playlist = listOf(song)
                startActivity<MainActivity>()
            }
            return songLayout
        }
    }
}