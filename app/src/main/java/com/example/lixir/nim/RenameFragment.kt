package com.example.lixir.nim

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.example.lixir.nim.backend.GameProcess


class RenameFragment : Fragment(), View.OnKeyListener, View.OnClickListener {

    private lateinit var enterNameForPlayer1: EditText
    private lateinit var enterNameForPlayer2: EditText
    private lateinit var enterNameForBot: EditText
    private lateinit var button: Button

    override fun onClick(v: View?) {
        (activity as MainActivity).nextFragment(MenuFragment())
    }

    override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
        if (event!!.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
            when (v!!.id) {
                R.id.enter_name_player_1 -> if (enterNameForPlayer1.text.toString() != "") GameProcess.player1.name =
                        enterNameForPlayer1.text.toString()
                R.id.enter_name_player_2 -> if (enterNameForPlayer2.text.toString() != "") GameProcess.player2.name =
                        enterNameForPlayer2.text.toString()
                R.id.enter_name_bot -> if (enterNameForBot.text.toString() != "") GameProcess.bot.name =
                        enterNameForBot.text.toString()
            }
            return true
        }
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val temp = inflater.inflate(R.layout.fragment_rename, container, false)
        enterNameForPlayer1 = temp.findViewById(R.id.enter_name_player_1)
        enterNameForPlayer2 = temp.findViewById(R.id.enter_name_player_2)
        enterNameForBot = temp.findViewById(R.id.enter_name_bot)
        button = temp.findViewById(R.id.return_menu)
        return temp
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        enterNameForBot.setOnKeyListener(this)
        enterNameForPlayer1.setOnKeyListener(this)
        enterNameForPlayer2.setOnKeyListener(this)
        button.setOnClickListener(this)
    }
}
