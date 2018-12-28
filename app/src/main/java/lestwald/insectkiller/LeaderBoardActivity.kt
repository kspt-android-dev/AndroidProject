package lestwald.insectkiller

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class LeaderBoardActivity : AppCompatActivity() {

    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leader_board)
        dbHelper = DBHelper(this)
        val database = dbHelper.readableDatabase
        val c = database.rawQuery("SELECT * FROM ${DBHelper.DB_NAME} Order By cast(${DBHelper.KEY_SCORE} as INTEGER) DESC", null)
        var i = 1
        if (c.moveToFirst()) {
            val nameColIndex = c.getColumnIndex(DBHelper.KEY_NAME)
            val scoreColIndex = c.getColumnIndex(DBHelper.KEY_SCORE)
            do {
                val nameTextView: TextView
                val scoreTextView: TextView
                when (i) {
                    1 -> {
                        nameTextView = findViewById(R.id.textView1)
                        scoreTextView = findViewById(R.id.textView2)
                        nameTextView.text = c.getString(nameColIndex)
                        scoreTextView.text = c.getInt(scoreColIndex).toString()
                    }
                    3 -> {
                        nameTextView = findViewById(R.id.textView3)
                        scoreTextView = findViewById(R.id.textView4)
                        nameTextView.text = c.getString(nameColIndex)
                        scoreTextView.text = c.getInt(scoreColIndex).toString()
                    }
                    5 -> {
                        nameTextView = findViewById(R.id.textView5)
                        scoreTextView = findViewById(R.id.textView6)
                        nameTextView.text = c.getString(nameColIndex)
                        scoreTextView.text = c.getInt(scoreColIndex).toString()
                    }
                    7 -> {
                        nameTextView = findViewById(R.id.textView7)
                        scoreTextView = findViewById(R.id.textView8)
                        nameTextView.text = c.getString(nameColIndex)
                        scoreTextView.text = c.getInt(scoreColIndex).toString()
                    }
                    9 -> {
                        nameTextView = findViewById(R.id.textView9)
                        scoreTextView = findViewById(R.id.textView10)
                        nameTextView.text = c.getString(nameColIndex)
                        scoreTextView.text = c.getInt(scoreColIndex).toString()
                    }
                }
                i += 2
                if (i == 11) break
            } while (c.moveToNext())
        } else c.close()
    }
}
