package com.example.lixir.nim

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.view.View.OnClickListener
import com.example.lixir.nim.backend.GameProcess
import kotlinx.android.synthetic.main.main_menu.*

class MenuFragment : Fragment(), OnClickListener {

    private lateinit var singleGameButton: Button
    private lateinit var duoGameButton: Button
    private lateinit var renameButton: Button
    private lateinit var infoButton: Button
    private lateinit var exitButton: Button

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.single_game_button -> {
                GameProcess.gameWithBot = true
                (activity as MainActivity).nextFragment(GameFragment())
            }
            R.id.duo_game_button -> {
                GameProcess.gameWithBot = false
                (activity as MainActivity).nextFragment(GameFragment())
            }
            R.id.rename_button -> (activity as MainActivity).nextFragment(RenameFragment())
            R.id.info_button -> (activity as MainActivity).nextActivity()
            R.id.exit_button -> activity!!.finish()
            else -> throw IllegalArgumentException()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val temp = inflater.inflate(R.layout.main_menu, container, false)
        singleGameButton = temp.findViewById(R.id.single_game_button)
        duoGameButton = temp.findViewById(R.id.duo_game_button)
        renameButton = temp.findViewById(R.id.rename_button)
        infoButton = temp.findViewById(R.id.info_button)
        exitButton = temp.findViewById(R.id.exit_button)
        return temp
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        singleGameButton.setOnClickListener(this)
        duoGameButton.setOnClickListener(this)
        renameButton.setOnClickListener(this)
        infoButton.setOnClickListener(this)
        exitButton.setOnClickListener(this)
    }

}