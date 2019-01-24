package sample.avightclav.checkers.activities


import android.content.Intent
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import sample.avightclav.checkers.dao.AppDatabase
import sample.avightclav.checkers.views.CheckersBoard
import sample.avightclav.checkers.R
import sample.avightclav.checkers.services.SaveGameService
import sample.avightclav.checkers.gamelogic.FieldSize
import sample.avightclav.checkers.gamelogic.Gameboard

class GameActivity : AppCompatActivity() {

    lateinit var checkersboard: Gameboard

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        if (intent != null) {
            val fieldSize = intent.getSerializableExtra("FIELD_SIZE") as FieldSize
            if (fieldSize != null)
                if (fieldSize != FieldSize.RESUME)
                    checkersboard = Gameboard(fieldSize)
                else {
                    val dbEntity = AppDatabase.get(this)
                    checkersboard = dbEntity.gameboardDAO().getLast().gameboard
                }
            else
                throw IllegalArgumentException("Intent doesn't contain FIELD_SIZE")
        }

        if (savedInstanceState != null) {
            checkersboard = savedInstanceState.getSerializable("checkerboard") as Gameboard
        }

        setContentView(R.layout.activity_game)
    }

    override fun onStart() {
        super.onStart()
        Log.d("GameActivity", "Started")
        val rootView = findViewById<ConstraintLayout>(R.id.field_layout)
        val checkersBoard = CheckersBoard(this, -1, checkersboard)
        checkersBoard.start()
        rootView.addView(checkersBoard)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("GameActivity", "Destroyed")

        if (isFinishing) {
            Log.d("GameActivity", "&Finishing")
            val saveGameDBServiceIntent = Intent(this, SaveGameService::class.java)
            saveGameDBServiceIntent.putExtra("checkerboard", checkersboard)
            startService(saveGameDBServiceIntent)
        }
    }

    public override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState.putSerializable("checkerboard", checkersboard)
    }
}