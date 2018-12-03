package com.dreamteam.monopoly

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.text.InputFilter
import android.widget.TextView
import com.dreamteam.monopoly.helpers.InputFilterMinMax
import com.dreamteam.monopoly.helpers.showToast
import maes.tech.intentanim.CustomIntent
import java.util.ArrayList


class AmountOfPlayersActivity : AppCompatActivity() {

    private var buttonEnter: Button? = null
    private var buttonBack: Button? = null
    private var buttonStart: Button? = null
    private var namespace: EditText? = null
    private var numberOfPlayers: Int = 0
    var playersNames: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_amount_of_players)

        buttonEnter = findViewById(R.id.buttonEnter)
        buttonBack = findViewById(R.id.buttonBack)
        buttonStart = findViewById(R.id.buttonStart)
        namespace = findViewById(R.id.Namespace)

        //namespace!!.setFilters(arrayOf<InputFilter>(InputFilterMinMax(0, 15)))

        buttonEnter!!.setOnClickListener{ v->
            numberOfPlayers++
            val content = namespace!!.getText().toString() //gets you the contents of edit text
            playersNames.add(content)
            val textViewId = getResources().getIdentifier("PlayerName${numberOfPlayers}", "id", packageName)
            val textView = findViewById<TextView>(textViewId)
            namespace!!.setText("")
            textView.setText(content)
            if (numberOfPlayers == 4) buttonEnter!!.isEnabled = false
        }

        buttonStart!!.setOnClickListener{ v->
            showToast(v, resources.getString(R.string.startGameToast))
            intent = Intent(this, GameActivity::class.java)
            intent.putExtra("PlayersNames" , playersNames)
            startActivity(intent)
            CustomIntent.customType(this, "bottom-to-up")
        }

        buttonBack!!.setOnClickListener { v->
            super.finish()
            CustomIntent.customType(this, "up-to-bottom")
        }
    }
}
