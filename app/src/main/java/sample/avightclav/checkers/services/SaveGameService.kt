package sample.avightclav.checkers.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import sample.avightclav.checkers.dao.AppDatabase
import sample.avightclav.checkers.dao.GameboardDBEntity
import sample.avightclav.checkers.gamelogic.Gameboard
import java.util.concurrent.TimeUnit

class SaveGameService: Service() {

    companion object {
        val broadcastTag = "SAVE_GAME_SERVICE"
        val state = "STATE"
        val SAVING_PENDED = 1
        val NO_SAVING_PENDED = 2
    }

    val TAG = "SaveGameService"

    val saveGameBinder = SaveGameBinder()
    @Volatile var pendingThreads = 0

    override fun onBind(intent: Intent?): Binder? {
        return saveGameBinder
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("GameActivity", "On Create")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("GameActivity", "On Start")
        if (intent != null) {
            val gameBoard = intent.getSerializableExtra("checkerboard") as Gameboard
            if (gameBoard != null)
                broadcastSaving()
                saveCurrentField(gameBoard)
        }
        return super.onStartCommand(intent, flags, startId)
    }

    inner class SaveGameBinder: Binder() {
        fun getService(): SaveGameService {
            return this@SaveGameService
        }
    }

    fun broadcastCurrentState() {
        LocalBroadcastManager.getInstance(this).sendBroadcast(Intent(broadcastTag).putExtra(
            state, if (pendingThreads == 0) NO_SAVING_PENDED else SAVING_PENDED
        ))
    }

    private fun broadcastSaving() {
        LocalBroadcastManager.getInstance(this).sendBroadcast(Intent(broadcastTag).putExtra(
            state,
            SAVING_PENDED
        ))
    }

    private fun broadcastNoSaving() {
        LocalBroadcastManager.getInstance(this).sendBroadcast(Intent(broadcastTag).putExtra(
            state,
            NO_SAVING_PENDED
        ))
    }

    private fun saveCurrentField(gameboard: Gameboard) {
        pendingThreads++
        Thread(Runnable {
            kotlin.run {
                Log.d(TAG, "Start saving thread $pendingThreads")
                TimeUnit.SECONDS.sleep(5) // Just to be sure that service really blocks button "RESUME"
                val dbEntity = AppDatabase.get(this)
                val gameboardDBEntity = GameboardDBEntity(null, gameboard)
                dbEntity.gameboardDAO().insertAll(gameboardDBEntity)
                pendingThreads--
                if (pendingThreads == 0) {
                    broadcastNoSaving()
                    stopSelf()
                }
            }
        }).start()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "Destroyed")
    }
}