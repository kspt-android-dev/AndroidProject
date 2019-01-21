package com.example.lixir.nim

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import com.example.lixir.nim.Utils.nextActivity
import com.example.lixir.nim.Utils.nextFragment
import com.example.lixir.nim.backend.GameProcess
import kotlinx.android.synthetic.main.main_menu.*
import kotlinx.android.synthetic.main.main_menu.view.*

class MenuFragment : Fragment(), OnClickListener {

    private lateinit var singleGameButton: Button
    private lateinit var duoGameButton: Button
    private lateinit var renameButton: Button
    private lateinit var infoButton: Button
    private lateinit var exitButton: Button

    override fun onClick(v: View?) {
        when (v!!) {
            single_game_button -> {
                GameProcess.gameWithBot = true
                (activity as MainActivity).nextFragment(GameFragment())
            }
            duo_game_button -> {
                GameProcess.gameWithBot = false
                (activity as MainActivity).nextFragment(GameFragment())
            }
            rename_button -> (activity as MainActivity).nextFragment(RenameFragment())
            info_button -> activity!!.nextActivity(InfoActivity().javaClass)
            exit_button -> activity!!.finish()
            else -> throw IllegalArgumentException()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val temp = inflater.inflate(R.layout.main_menu, container, false)
        singleGameButton = temp.single_game_button
        duoGameButton = temp.duo_game_button
        renameButton = temp.rename_button
        infoButton = temp.info_button
        exitButton = temp.exit_button
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