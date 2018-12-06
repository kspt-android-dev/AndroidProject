package com.dreamteam.monopoly

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.text.InputFilter
import android.widget.TextView
import android.widget.Toast
import com.dreamteam.monopoly.helpers.InputFilterMinMax
import com.dreamteam.monopoly.helpers.showToast
import es.dmoral.toasty.Toasty
import maes.tech.intentanim.CustomIntent
import java.util.ArrayList


class AmountOfPlayersActivity : AppCompatActivity() {

    private var maxPlayers: Int = 4
    private var buttonEnter: Button? = null
    private var buttonBack: Button? = null
    private var buttonStart: Button? = null
    private var buttonDelete: Button? = null
    private var namespace: EditText? = null
    private var numberOfPlayers: Int = 0
    var playersNames: ArrayList<String> = ArrayList()
    var deleteList: ArrayList<TextView> = ArrayList()
    var arrayOfTextViews: ArrayList<TextView> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_amount_of_players)

        buttonEnter = findViewById(R.id.buttonEnter)
        buttonBack = findViewById(R.id.buttonBack)
        buttonStart = findViewById(R.id.buttonStart)
        buttonDelete = findViewById(R.id.buttonDelete)
        namespace = findViewById(R.id.Namespace)

        //namespace!!.setFilters(arrayOf<InputFilter>(InputFilterMinMax(0, 15)))

        if (numberOfPlayers < 2) buttonStart!!.isEnabled = false

        for (i in 1..maxPlayers) {
            val textViewId = resources.getIdentifier("PlayerName$i", "id", packageName)
            val textView = findViewById<TextView>(textViewId)
            arrayOfTextViews.add(textView)
        }

        buttonEnter!!.setOnClickListener { v ->
            val content = namespace!!.text.toString() //gets you the contents of edit text
            if (content.isEmpty() || content.length > 15) {
                //showToast(v, resources.getString(R.string.nameSize))
                Toasty.error(this, resources.getString(R.string.nameSize),
                        Toast.LENGTH_SHORT, true).show()
                return@setOnClickListener
            }
            numberOfPlayers++
            playersNames.add(content)
            Toasty.success(this, resources.getString(R.string.newPlayer) + content,
                    Toast.LENGTH_SHORT, true).show()
            val textViewId = resources.getIdentifier("PlayerName$numberOfPlayers", "id", packageName)
            val textView = findViewById<TextView>(textViewId)
            namespace!!.setText("")
            textView.text = content
            textView.isClickable = true


            textView!!.setOnClickListener {
                if (!deleteList.contains(textView)) {
                    textView.setBackgroundColor(resources.getColor(R.color.colorGrey))
                    deleteList.add(textView)
                } else {
                    textView.setBackgroundResource(android.R.color.transparent)
                    deleteList.remove(textView)
                }
            }


            if (numberOfPlayers == 4) buttonEnter!!.isEnabled = false
            if (numberOfPlayers >= 2) buttonStart!!.isEnabled = true
            moveAllNames()
        }

        buttonStart!!.setOnClickListener {
            Toasty.normal(this, resources.getString(R.string.startGameToast),
                    Toast.LENGTH_SHORT, resources.getDrawable(R.drawable.dice6)).show()
            intent = Intent(this, GameActivity::class.java)
            intent.putExtra("PlayersNames", playersNames)
            startActivity(intent)
            CustomIntent.customType(this, "bottom-to-up")
        }

        buttonBack!!.setOnClickListener {
            super.finish()
            CustomIntent.customType(this, "up-to-bottom")
        }

        buttonDelete!!.setOnClickListener {
            for (txtView in deleteList) {
                Toasty.error(this, txtView.text.toString() + resources.getString(R.string.removed),
                        Toast.LENGTH_SHORT, true).show()
                playersNames.remove(txtView.text.toString())
                txtView.text = ""
                numberOfPlayers--
                txtView.setBackgroundResource(android.R.color.transparent)
            }
            deleteList.clear()
            moveAllNames()
            setUnclickble()
            if (numberOfPlayers < 2) buttonStart!!.isEnabled = false
            if (numberOfPlayers < 4) buttonEnter!!.isEnabled = true
        }


    }

    private fun moveAllNames() {
        for ((index, txtView) in arrayOfTextViews.withIndex()) {
            if (txtView.text == "") {
                for (i in index until arrayOfTextViews.size)
                    if (arrayOfTextViews[i].text != "") {
                        txtView.text = arrayOfTextViews[i].text
                        arrayOfTextViews[i].text = ""
                        break
                    }
            }
        }
    }

    fun setUnclickble() {
        for (text in arrayOfTextViews) {
            if (text.text == "")
                text.isClickable = false
        }
    }
}
