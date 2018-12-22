package com.dreamteam.monopoly

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.widget.Button
import android.widget.EditText
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import es.dmoral.toasty.Toasty
import maes.tech.intentanim.CustomIntent
import kotlin.collections.ArrayList


class AmountOfPlayersActivity : AppCompatActivity() {

    private var maxPlayers: Int = 4
    private var buttonEnter: Button? = null
    private var buttonBack: Button? = null
    private var buttonStart: Button? = null
    private var buttonDelete: Button? = null
    private var buttonAIfirst: Button? = null
    private var buttonAIsecond: Button? = null
    private var buttonAIthird: Button? = null
    private var buttonAIfourth: Button? = null
    private var namespace: EditText? = null
    private var numberOfPlayers: Int = 0
    private var playersNames: ArrayList<String> = ArrayList()
    private var deleteList: ArrayList<TextView> = ArrayList()
    private var arrayOfTextViews: ArrayList<TextView> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_amount_of_players)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        hideTopBar()

        buttonEnter = findViewById(R.id.buttonEnter)
        buttonBack = findViewById(R.id.buttonBack)
        buttonStart = findViewById(R.id.buttonStart)
        buttonDelete = findViewById(R.id.buttonDelete)
        namespace = findViewById(R.id.Namespace)
        buttonAIfirst = findViewById(R.id.aiButton1)
        buttonAIsecond = findViewById(R.id.aiButton2)
        buttonAIthird = findViewById(R.id.aiButton3)
        buttonAIfourth = findViewById(R.id.aiButton4)

        buttonAIfirst!!.visibility = View.INVISIBLE
        buttonAIfirst!!.isClickable = false
        buttonAIsecond!!.visibility = View.INVISIBLE
        buttonAIsecond!!.isClickable = false
        buttonAIthird!!.visibility = View.INVISIBLE
        buttonAIthird!!.isClickable = false
        buttonAIfourth!!.visibility = View.INVISIBLE
        buttonAIfourth!!.isClickable = false

        buttonAIfirst!!.setOnClickListener {
            buttonAiListner(buttonAIfirst!!)
        }
        buttonAIsecond!!.setOnClickListener {
            buttonAiListner(buttonAIsecond!!)
        }
        buttonAIthird!!.setOnClickListener {
            buttonAiListner(buttonAIthird!!)
        }
        buttonAIfourth!!.setOnClickListener {
            buttonAiListner(buttonAIfourth!!)
        }

        if (numberOfPlayers < 2) buttonStart!!.isEnabled = false

        for (i in 1..maxPlayers) {
            val textViewId = resources.getIdentifier("PlayerName$i", "id", packageName)
            val textView = findViewById<TextView>(textViewId)
            arrayOfTextViews.add(textView)
        }

        buttonEnter!!.setOnClickListener {
            val content = namespace!!.text.toString() //gets you the contents of edit text
            if (content.isEmpty() || content.length > 15 || playersNames.contains(content)) {
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
                    textView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorGrey))
                    deleteList.add(textView)
                } else {
                    textView.setBackgroundResource(android.R.color.transparent)
                    deleteList.remove(textView)
                }
            }

            aiButtonsAppearence(true)


            if (numberOfPlayers == maxPlayers) buttonEnter!!.isEnabled = false
            if (numberOfPlayers >= 2) buttonStart!!.isEnabled = true
            moveAllNames()
        }

        buttonStart!!.setOnClickListener {
            val players: ArrayList<String> = ArrayList()
            val bots: ArrayList<String> = ArrayList()
            val names = HashMap<String, ArrayList<String>>()
            for (i in 1..numberOfPlayers)
            {
                val buttonID = this.resources.getIdentifier("aiButton$i", "id", packageName)
                val aiButton = findViewById<Button>(buttonID)
                if (aiButton.text.toString() == "AI: OFF")
                    players.add(playersNames[i - 1])
                else
                    bots.add(playersNames[i - 1])
            }
            names["OFF"] = bots
            names["OFF"] = players
            intent = Intent(this, GameActivity::class.java)
            intent.putExtra("Map", names)
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

            aiButtonsAppearence(false)

            if (numberOfPlayers < 2) buttonStart!!.isEnabled = false
            if (numberOfPlayers < 4) buttonEnter!!.isEnabled = true
        }

    }

    override fun onResume() {
        super.onResume()
        hideTopBar()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        hideTopBar()
    }

    private fun hideTopBar() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
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

    private fun setUnclickble() {
        for (text in arrayOfTextViews) {
            if (text.text == "")
                text.isClickable = false
        }
    }


    private fun buttonAiListner(button: Button) {
        Log.d("CHECKTEXT", button.text.toString())
        if (button.text == "AI: ON") button.text = resources.getString(R.string.offAI)
        else button.text = resources.getString(R.string.onAI)
    }

    private fun aiButtonsAppearence(on: Boolean) {
        if (on) {
            for (i in 1..numberOfPlayers) {
                val buttonID = this.resources.getIdentifier("aiButton$i", "id", packageName)
                val aiButton = findViewById<Button>(buttonID)
                aiButton.visibility = View.VISIBLE
                aiButton.isClickable = true
            }
        } else {
            for (i in numberOfPlayers + 1..maxPlayers) {
                val buttonID = this.resources.getIdentifier("aiButton$i", "id", packageName)
                val aiButton = findViewById<Button>(buttonID)
                aiButton.visibility = View.INVISIBLE
                aiButton.isClickable = false
            }
        }
    }
}
