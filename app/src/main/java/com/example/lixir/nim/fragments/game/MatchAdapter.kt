package com.example.lixir.nim.fragments.game

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.example.lixir.nim.R.layout.match_list_item
import com.example.lixir.nim.R.string.turn_player
import com.example.lixir.nim.backend.GameProcess
import kotlinx.android.synthetic.main.match_list_item.view.*

class MatchAdapter(
    private var fragment: GameFragment,
    resource: Int,
    var row: Int
) : RecyclerView.Adapter<MatchAdapter.ViewHolder>() {

    var layout: Int = resource

    class ViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {
        var button: ImageButton = view!!.match_image_button
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                match_list_item,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = GameProcess.rows[row]

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.button.setOnClickListener {
            fragment.synchronize()
            GameProcess.play(row, position)
            fragment.synchronize()
            fragment.textView.text = ("${fragment.context!!.getString(turn_player)} ${GameProcess.currentPlayer.name}")
            if (GameProcess.endGame) {
                val endGameDialogFragment = EndGameDialogFragment()
                endGameDialogFragment.show(fragment.activity!!.supportFragmentManager, "dialog")
            }
        }
    }

}