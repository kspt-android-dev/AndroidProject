package lestwald.insectkiller

import android.content.ContentValues
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class GameOverActivity : AppCompatActivity() {

    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)
        dbHelper = DBHelper(this)
        val finalScoreTextView = findViewById<TextView>(R.id.final_score)
        val finalScore = intent.getIntExtra("finalScore", 0)
        finalScoreTextView.text = ("YOUR SCORE\n$finalScore")
        val userName = findViewById<EditText>(R.id.user_name)
        val saveResultButton = findViewById<Button>(R.id.save_result_button)
        saveResultButton.setOnClickListener {
            if (userName.text.isNotEmpty()) {
                val database = dbHelper.writableDatabase
                val contentValues = ContentValues()
                contentValues.put(DBHelper.KEY_NAME, userName.text.toString())
                contentValues.put(DBHelper.KEY_SCORE, finalScore)
                database.insert(DBHelper.DB_NAME, null, contentValues)
                intent = Intent(this, LeaderBoardActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}
