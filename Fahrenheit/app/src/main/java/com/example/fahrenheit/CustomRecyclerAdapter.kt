package com.example.fahrenheit

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomRecyclerAdapter(val mainActivity: MainActivity) : RecyclerView.Adapter<CustomRecyclerAdapter.CustomViewHolder>() {

    private lateinit var labelView: View

    private lateinit var answerView: View

    private var list = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        labelView = LayoutInflater.from(parent.context).inflate(R.layout.custom_card_view, parent, false)
        answerView = LayoutInflater.from(parent.context).inflate(R.layout.custom_card_view_answer, parent, false)
        return CustomViewHolder(labelView)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.onBind(position)
    }

    fun push(s: String) {
        list.add(s)
        notifyDataSetChanged()
    }

    inner class CustomViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        private val textView = view.findViewById<TextView>(R.id.textLabel)!!
        fun onBind(position: Int) {
            textView.text = list[position]
        }
    }
}