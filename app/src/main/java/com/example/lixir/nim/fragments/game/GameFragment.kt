package com.example.lixir.nim.fragments.game

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.lixir.nim.R
import com.example.lixir.nim.backend.Constants
import com.example.lixir.nim.backend.GameProcess
import kotlinx.android.synthetic.main.game_fragment.*
import kotlinx.android.synthetic.main.game_fragment.view.*
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

class GameFragment : Fragment() {
    private lateinit var adapter: Array<MatchAdapter>
    private lateinit var views: Array<RecyclerView>
    lateinit var textView: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        GameProcess.newGame()
    }

    override fun onCreateView(
        @NotNull inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val temp = inflater.inflate(R.layout.game_fragment, container, false)
        textView = temp.current_player_text_view
        return temp
    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        views = arrayOf(rv_0, rv_1, rv_2, rv_3)
        adapter = Array(Constants.MAIN_LIST.size){
            MatchAdapter(
                this,
                R.layout.match_list_item,
                0
            )
        }
        for (i in 0 until adapter.size) {
            adapter[i].row = i
            views[i].adapter = adapter[i]
            views[i].layoutManager = LinearLayoutManager(context)
        }
    }

    private fun removeAtPosition(position: Int, row: Int) {
        if (row >= Constants.MAIN_LIST.size) throw IllegalArgumentException()
        adapter[row].notifyItemRangeRemoved(position, adapter[row].itemCount)
    }

    fun synchronize(){
        GameProcess.rows.forEachIndexed { index, i -> removeAtPosition(i, index) }
    }
}