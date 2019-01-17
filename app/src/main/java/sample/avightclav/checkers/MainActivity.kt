package sample.avightclav.checkers

import android.content.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Button
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"

    lateinit var saveGameService: SaveGameService
    var bound = false

    val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent != null) {
                if (intent.action == SaveGameService.broadcastTag) {
                    val isNotSaving = intent.getIntExtra(SaveGameService.state, -1) == SaveGameService.SAVING_PENDED
                    findViewById<Button>(R.id.resume).isEnabled = !isNotSaving
                }
            }
        }
    }

    val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            var binder = service as SaveGameService.SaveGameBinder
            saveGameService = binder.getService()
            bound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            bound = false
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindService(Intent(this, SaveGameService::class.java), serviceConnection, Context.BIND_AUTO_CREATE)

        Log.d(TAG, "Started")
        val resumeButton = findViewById<Button>(R.id.resume)

        findViewById<Button>(R.id.start_game_8).setOnClickListener { it ->
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra("FIELD_SIZE", FieldSize.RUSSIAN)
            startActivity(intent)
        }

        findViewById<Button>(R.id.start_game_10).setOnClickListener { it ->
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra("FIELD_SIZE", FieldSize.WORLDWIDE)
            startActivity(intent)
        }

        resumeButton.setOnClickListener { it ->
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra("FIELD_SIZE", FieldSize.RESUME)
            startActivity(intent)
        }

        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, IntentFilter(SaveGameService.broadcastTag))
    }

    override fun onStop() {
        super.onStop()

        if (bound) {
            unbindService(serviceConnection)
            bound = false
        }

    }
}
