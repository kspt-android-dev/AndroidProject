package com.dreamteam.monopoly

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.widget.Button
import android.widget.EditText
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.dreamteam.monopoly.game.GameData.maxNameLength
import com.dreamteam.monopoly.game.GameData.maxPlayers
import com.dreamteam.monopoly.game.GameData.minPlayers
import com.dreamteam.monopoly.game.player.PlayerType
import com.dreamteam.monopoly.helpers.shakeEffect
import es.dmoral.toasty.Toasty
import maes.tech.intentanim.CustomIntent
import kotlin.collections.ArrayList


class AmountOfPlayersActivity : AppCompatActivity() {

    private var buttonEnter: Button? = null
    private var buttonBack: Button? = null
    private var buttonStart: Button? = null
    private var buttonDelete: Button? = null
    private var buttonAiFirst: Button? = null
    private var buttonAiSecond: Button? = null
    private var buttonAiThird: Button? = null
    private var buttonAiFourth: Button? = null
    private var namespace: EditText? = null
    private var numberOfPlayers: Int = 0
    private var playersNames: ArrayList<String> = ArrayList(maxPlayers)
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
        buttonAiFirst = findViewById(R.id.aiButton1)
        buttonAiSecond = findViewById(R.id.aiButton2)
        buttonAiThird = findViewById(R.id.aiButton3)
        buttonAiFourth = findViewById(R.id.aiButton4)

        hideAllNames()

        if (savedInstanceState != null) {
            dataRestore(savedInstanceState)
        }

        buttonAiFirst!!.setOnClickListener {
            buttonAiListener(buttonAiFirst!!)
        }
        buttonAiSecond!!.setOnClickListener {
            buttonAiListener(buttonAiSecond!!)
        }
        buttonAiThird!!.setOnClickListener {
            buttonAiListener(buttonAiThird!!)
        }
        buttonAiFourth!!.setOnClickListener {
            buttonAiListener(buttonAiFourth!!)
        }

        if (numberOfPlayers < minPlayers) buttonStart!!.isEnabled = false

        for (i in 1..maxPlayers) {
            val textViewId = resources.getIdentifier(getString(R.string.playerName) + i, getString(R.string.id), packageName)
            val textView = findViewById<TextView>(textViewId)
            arrayOfTextViews.add(textView)
        }

        buttonEnter!!.setOnClickListener {
            val content = namespace!!.text.toString() //gets you the contents of edit text
            if (content.isEmpty() || content.length > maxNameLength) {
                Toasty.error(this, resources.getString(R.string.nameSize),
                        Toast.LENGTH_SHORT, true).show()
                return@setOnClickListener
            }

            if (playersNames.contains(content)) {
                Toasty.error(this, resources.getString(R.string.containtsString),
                        Toast.LENGTH_SHORT, true).show()
                return@setOnClickListener
            }
            countNewPlayer(content)

            Toasty.success(this, resources.getString(R.string.newPlayer) + content,
                    Toast.LENGTH_SHORT, true).show()
            val textViewId = resources.getIdentifier(getString(R.string.playerName) + numberOfPlayers, getString(R.string.id), packageName)
            val textView = findViewById<TextView>(textViewId)
            namespace!!.setText("")
            textView.text = content
            textView.isClickable = true

            playerTextListener(textView)

            aiButtonsAppearance(true)

            if (numberOfPlayers == maxPlayers) buttonEnter!!.isEnabled = false
            if (numberOfPlayers >= minPlayers) buttonStart!!.isEnabled = true
            moveAllNames()
            shakeEffect(buttonEnter!!)
        }

        buttonStart!!.setOnClickListener {
            val players: ArrayList<String> = ArrayList()
            val bots: ArrayList<String> = ArrayList()
            val names = HashMap<PlayerType, ArrayList<String>>()
            for (i in 1..numberOfPlayers) {
                val buttonID = this.resources.getIdentifier(getString(R.string.aiButton) + i, getString(R.string.id), packageName)
                val aiButton = findViewById<Button>(buttonID)
                if (aiButton.text.toString() == resources.getString(R.string.offAI))
                    players.add(playersNames[i - 1])
                else
                    bots.add(playersNames[i - 1])
            }
            names[PlayerType.AI] = bots
            names[PlayerType.PERSON] = players

            shakeEffect(buttonStart!!)
            intent = Intent(this, GameActivity::class.java)
            intent.putExtra(getString(R.string.playersMap), names)
            startActivity(intent)
            CustomIntent.customType(this, getString(R.string.bottom_to_up))
        }

        buttonBack!!.setOnClickListener {
            super.finish()
            shakeEffect(buttonBack!!)
            CustomIntent.customType(this, getString(R.string.up_to_bottom))
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
            setUnClickable()

            aiButtonsAppearance(false)

            if (numberOfPlayers < minPlayers) buttonStart!!.isEnabled = false
            if (numberOfPlayers < maxPlayers) buttonEnter!!.isEnabled = true
            shakeEffect(buttonDelete!!)
        }
    }

    @Override
    override fun finish() {
        super.finish()
        CustomIntent.customType(this, getString(R.string.up_to_bottom))
    }

    override fun onResume() {
        super.onResume()
        hideTopBar()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        hideTopBar()
    }

    private fun countNewPlayer(content: String) {
        numberOfPlayers++
        playersNames.add(content)
    }


    private fun hideTopBar() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
    }

    private fun hideAllNames() {
        buttonAiFirst!!.visibility = View.INVISIBLE
        buttonAiFirst!!.isClickable = false
        buttonAiSecond!!.visibility = View.INVISIBLE
        buttonAiSecond!!.isClickable = false
        buttonAiThird!!.visibility = View.INVISIBLE
        buttonAiThird!!.isClickable = false
        buttonAiFourth!!.visibility = View.INVISIBLE
        buttonAiFourth!!.isClickable = false
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

    private fun setUnClickable() {
        for (text in arrayOfTextViews) {
            if (text.text == "")
                text.isClickable = false
        }
    }

    private fun playerTextListener(playerView: TextView) {
        playerView.setOnClickListener {
            if (!deleteList.contains(playerView)) {
                playerView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorGrey))
                deleteList.add(playerView)
            } else {
                playerView.setBackgroundResource(android.R.color.transparent)
                deleteList.remove(playerView)
            }
        }
    }

    private fun buttonAiListener(button: Button) {
        if (button.text == resources.getString(R.string.onAI)) button.text = resources.getString(R.string.offAI)
        else button.text = resources.getString(R.string.onAI)
    }

    private fun aiButtonsAppearance(on: Boolean) {
        if (on) {
            for (i in 1..numberOfPlayers) {
                val buttonID = this.resources.getIdentifier(getString(R.string.aiButton) + i, getString(R.string.id), packageName)
                val aiButton = findViewById<Button>(buttonID)
                aiButton.visibility = View.VISIBLE
                aiButton.isClickable = true
            }
        } else {
            for (i in numberOfPlayers + 1..maxPlayers) {
                val buttonID = this.resources.getIdentifier(getString(R.string.aiButton) + i, getString(R.string.id), packageName)
                val aiButton = findViewById<Button>(buttonID)
                aiButton.visibility = View.INVISIBLE
                aiButton.isClickable = false
            }
        }
    }

    private fun restorePlayersList(savedInstanceState: Bundle) {
        playersNames = savedInstanceState.getStringArrayList(getString(R.string.playersMap))
        val pDells: ArrayList<String> = savedInstanceState.getStringArrayList(getString(R.string.deletePlayersNames))
        val pTypes: BooleanArray = savedInstanceState.getBooleanArray(getString(R.string.playersTypes))
        numberOfPlayers = playersNames.size
        deleteList.clear()
        for (i in 1..numberOfPlayers) {
            val textViewID = this.resources.getIdentifier(getString(R.string.playerName) + i, getString(R.string.id), packageName)
            val pName = findViewById<TextView>(textViewID)
            pName.text = playersNames[i - 1]

            playerTextListener(pName)

            if (pDells.contains(playersNames[i - 1])) {
                pName.setBackgroundColor(ContextCompat.getColor(this, R.color.colorGrey))
                deleteList.add(pName)
            }

            val aiButtonID = this.resources.getIdentifier(getString(R.string.aiButton) + i, getString(R.string.id), packageName)
            val aiButton = findViewById<Button>(aiButtonID)
            if (pTypes[i - 1]) aiButton.text = resources.getString(R.string.onAI)
            else aiButton.text = resources.getString(R.string.offAI)
            aiButton.visibility = View.VISIBLE
            aiButton.isClickable = true
        }
    }

    private fun dataRestore(savedInstanceState: Bundle) {
        namespace!!.setText(savedInstanceState.getString(getString(R.string.keyboardText)))
        restorePlayersList(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        val deletePlayersNames: ArrayList<String> = ArrayList()
        for (p in deleteList)
            deletePlayersNames.add(p.text.toString())

        outState?.putStringArrayList(getString(R.string.playersNames), playersNames)
        outState?.putStringArrayList(getString(R.string.deletePlayersNames), deletePlayersNames)
        outState?.putBooleanArray(getString(R.string.playersTypes), loadPlayersTypes())
        outState?.putString(getString(R.string.keyboardText), namespace!!.text.toString())
        super.onSaveInstanceState(outState)
    }

    private fun loadPlayersTypes(): BooleanArray {
        val playersTypes: ArrayList<Boolean> = ArrayList()
        for (i in 1..playersNames.size) {
            val aiButton = findViewById<Button>(resources.getIdentifier(getString(R.string.aiButton) + i, getString(R.string.id), packageName))
            if (aiButton.text.toString() == resources.getString(R.string.offAI))
                playersTypes.add(false)
            else
                playersTypes.add(true)
        }
        return playersTypes.toBooleanArray()
    }
}
