package com.example.lixir.nim

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lixir.nim.Utils.nextFragment
import com.example.lixir.nim.backend.Constants
import com.example.lixir.nim.backend.GameProcess

class EndGameDialogFragment: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(activity!!)
            .setMessage("${Constants.WIN} ${GameProcess.winner.name}")
            .setPositiveButton(
                context!!.getString(R.string.return_menu)
            ) { dialog, which ->
                (activity as MainActivity).nextFragment(MenuFragment())
            }
            .setNegativeButton(
                Constants.NEW_GAME
            ) { dialog, which ->
                (activity as MainActivity).nextFragment(GameFragment())
            }
            .create()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        isCancelable = false
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}