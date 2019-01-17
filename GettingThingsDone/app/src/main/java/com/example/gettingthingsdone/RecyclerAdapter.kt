package com.example.gettingthingsdone

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.gettingthingsdone.R.id.*
import com.example.gettingthingsdone.db.entity.File


class RecyclerAdapter(private val fragment: MainFragment) : RecyclerView.Adapter<RecyclerAdapter.CustomViewHolder>() {

    private var files: List<File> = listOf()
    private val context = fragment.context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.custom_card_view, parent, false)
        return CustomViewHolder(view)
    }

    override fun getItemCount() = files.size

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.onBind(position)
    }

    fun setFiles(filesSetting: List<File>) {
        files = filesSetting
        this.notifyDataSetChanged()
    }


    inner class CustomViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view)
        , PopupMenu.OnMenuItemClickListener {

        private val img = view.findViewById<ImageView>(R.id.ic_folder)
        val text = view.findViewById<TextView>(R.id.text)!!

        fun onBind(position: Int) {
            val file = files[position]
            text.text = file.text
            if (file.isFolder)
                img.setImageResource(R.drawable.ic_folder_black)
            else
                img.setImageResource(R.drawable.ic_note)
            itemView.setOnLongClickListener {
                showPopup(itemView)
                return@setOnLongClickListener true
            }
            itemView.setOnClickListener {
                if (file.isFolder){
                    fragment.openFolder(file)
                }else {
                    fragment.openNote(file)
                }
            }
        }

        private fun showPopup(view: View) {
            val popup = PopupMenu(context, view)
            popup.setOnMenuItemClickListener(this)
            popup.inflate(R.menu.popup_menu)
            popup.show()
        }

        override fun onMenuItemClick(item: MenuItem?): Boolean {
            when (item!!.itemId) {
                menu_rename -> {
                    Toast.makeText(context, "Rename", Toast.LENGTH_SHORT).show()
                    return true
                }
                menu_copy -> {
                    Toast.makeText(context, "Copy", Toast.LENGTH_SHORT).show()
                    return true
                }
                menu_move -> {
                    Toast.makeText(context, "Move", Toast.LENGTH_SHORT).show()
                    return true
                }
                menu_delete -> {
                    Toast.makeText(context, "Delete", Toast.LENGTH_SHORT).show()
                    return true
                }
                else -> return false
            }
        }
    }
}