package com.dreamteam.monopoly

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.text.InputFilter
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.dreamteam.monopoly.helpers.InputFilterMinMax
import com.dreamteam.monopoly.helpers.showToast
import es.dmoral.toasty.Toasty
import maes.tech.intentanim.CustomIntent
import kotlin.collections.ArrayList


class AmountOfPlayersActivity : AppCompatActivity() {
    private var maxPlayers: Int = 4
    private var buttonEnter: Button? = null
    private var buttonBack: Button? = null
    private var buttonStart: Button? = null
    private var buttonDelete: Button? = null
    private var buttonAI_first: Button? = null
    private var buttonAI_second: Button? = null
    private var buttonAI_third: Button? = null
    private var buttonAI_fourth: Button? = null
    private var namespace: EditText? = null
    private var numberOfPlayers: Int = 0
    var playersNames: ArrayList<String> = ArrayList()
    var deleteList: ArrayList<TextView> = ArrayList()
    var arrayOfTextViews: ArrayList<TextView> = ArrayList()
    var maxPlayer: Int = 4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_amount_of_players)

        hideTopBar()

        buttonEnter = findViewById(R.id.buttonEnter)
        buttonBack = findViewById(R.id.buttonBack)
        buttonStart = findViewById(R.id.buttonStart)
        buttonDelete = findViewById(R.id.buttonDelete)
        namespace = findViewById(R.id.Namespace)
        buttonAI_first = findViewById(R.id.aiButton1)
        buttonAI_second = findViewById(R.id.aiButton2)
        buttonAI_third = findViewById(R.id.aiButton3)
        buttonAI_fourth = findViewById(R.id.aiButton4)

        buttonAI_first!!.visibility = View.INVISIBLE
        buttonAI_first!!.isClickable = false
        buttonAI_second!!.visibility = View.INVISIBLE
        buttonAI_second!!.isClickable = false
        buttonAI_third!!.visibility = View.INVISIBLE
        buttonAI_third!!.isClickable = false
        buttonAI_fourth!!.visibility = View.INVISIBLE
        buttonAI_fourth!!.isClickable = false

        buttonAI_first!!.setOnClickListener {
            buttonAiListner(buttonAI_first!!)
        }
        buttonAI_second!!.setOnClickListener {
            buttonAiListner(buttonAI_second!!)
        }
        buttonAI_third!!.setOnClickListener {
            buttonAiListner(buttonAI_third!!)
        }
        buttonAI_fourth!!.setOnClickListener {
            buttonAiListner(buttonAI_fourth!!)
        }

        if (numberOfPlayers < 2) buttonStart!!.isEnabled = false

        for (i in 1..maxPlayers) {
            val textViewId = resources.getIdentifier("PlayerName$i", "id", packageName)
            val textView = findViewById<TextView>(textViewId)
            arrayOfTextViews.add(textView)
        }

        buttonEnter!!.setOnClickListener { v ->
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
                    textView.setBackgroundColor(resources.getColor(R.color.colorGrey))
                    deleteList.add(textView)
                } else {
                    textView.setBackgroundResource(android.R.color.transparent)
                    deleteList.remove(textView)
                }


            }

            aiButtonsAppearence(true)


            if (numberOfPlayers == 4) buttonEnter!!.isEnabled = false
            if (numberOfPlayers >= 2) buttonStart!!.isEnabled = true
            moveAllNames()
        }

        buttonStart!!.setOnClickListener {
            val players: ArrayList<String> = ArrayList()
            val bots: ArrayList<String> = ArrayList()
            val names = HashMap<String, ArrayList<String>>()
            for (i in 1..numberOfPlayers) {
                val buttonID = this.resources.getIdentifier("aiButton${i}", "id", packageName)
                val aiButton = findViewById<Button>(buttonID)
                if (aiButton.text.toString() == "AI: OFF")
                    players.add(playersNames[i - 1])
                else
                    bots.add(playersNames[i - 1])
            }
            names.set("ON", bots!!)
            names.set("OFF", players!!)
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

        if (savedInstanceState != null) {
            dataRestore(savedInstanceState)
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

    fun setUnclickble() {
        for (text in arrayOfTextViews) {
            if (text.text == "")
                text.isClickable = false
        }
    }

    private fun buttonAiListner(button: Button) {
        Log.d("CHECKTEXT", button.text.toString())
        if (button.text.toString() == "AI: ON") button.setText("AI: OFF")
        else button.setText("AI: ON")
    }

    private fun aiButtonsAppearence(on: Boolean) {
        if (on) {
            for (i in 1..numberOfPlayers) {
                val buttonID = this.resources.getIdentifier("aiButton${i}", "id", packageName)
                val aiButton = findViewById<Button>(buttonID)
                aiButton.visibility = View.VISIBLE
                aiButton.isClickable = true
            }
        } else {
            for (i in numberOfPlayers + 1..maxPlayers) {
                val buttonID = this.resources.getIdentifier("aiButton${i}", "id", packageName)
                val aiButton = findViewById<Button>(buttonID)
                aiButton.visibility = View.INVISIBLE
                aiButton.isClickable = false
            }
        }
    }

    private fun dataRestore(savedInstanceState: Bundle) {

    }

    override fun onSaveInstanceState(outState: Bundle?) {
        val playersNames: ArrayList<String> = ArrayList(maxPlayers)
        val playersTypes: ArrayList<Int> = ArrayList(maxPlayers)

        outState?.putStringArrayList("playersNames", playersNames)
        outState?.putIntegerArrayList("playersTypes", playersTypes)
        super.onSaveInstanceState(outState)
    }
}
