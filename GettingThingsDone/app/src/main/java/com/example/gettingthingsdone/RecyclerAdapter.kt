package com.example.gettingthingsdone

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.gettingthingsdone.R.id.*
import com.example.gettingthingsdone.db.entity.File
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class RecyclerAdapter(private val fragment: MainFragment) : RecyclerView.Adapter<RecyclerAdapter.CustomViewHolder>() {

    private var movingFile: File? = null
    private var files: MutableList<File> = mutableListOf()
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
        files = filesSetting.toMutableList()
        this.notifyDataSetChanged()
    }

    fun notifyFile(file: File) {
        val index = files.indexOf(file)
        notifyItemChanged(index)
    }


    inner class CustomViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {

        private val img = view.findViewById<ImageView>(R.id.ic_folder)
        private val textView = view.findViewById<TextView>(R.id.text)!!
        private val onclickAnim = AnimationUtils.loadAnimation(context, R.anim.onclick)

        fun onBind(position: Int) {
            val file = files[position]
            val noteText = file.text!!
            if (movingFile == null) {
                if (noteText.length > 20) {
                    textView.text = (noteText.substring(0, 17) + "...")
                } else textView.text = noteText
                if (file.isFolder)
                    img.setImageResource(R.drawable.ic_folder_black)
                else
                    img.setImageResource(R.drawable.ic_note)
                itemView.setOnLongClickListener {
                    //                    itemView.startAnimation(onclickAnim)
                    showPopup(itemView, position)
                    return@setOnLongClickListener true
                }
                itemView.setOnClickListener {
                    if (file.isFolder) {
                        fragment.openFolder(file)
                    } else {
                        fragment.openNote(file)
                    }
                }
            } else {
                img.setImageResource(R.drawable.ic_folder_black)
                textView.text = noteText
                itemView.setOnClickListener {
                    showConfirmDialog(files[position])
                }
            }
        }

        private fun showConfirmDialog(file: File) {
            val builder = AlertDialog.Builder(context)
            val dialogView = fragment.layoutInflater.inflate(R.layout.custom_cancel_dialog, null)
            val text = dialogView.findViewById<TextView>(R.id.alarm)
            text.text = "Move the file to current folder?"
            builder.setView(dialogView)
            val btnPos = dialogView.findViewById<Button>(R.id.dialog_pos_btn)
            val btnNeg = dialogView.findViewById<Button>(R.id.dialog_neg_btn)
            val dialog = builder.create()
            btnPos.setOnClickListener {
                dialog.cancel()
                GlobalScope.launch(Dispatchers.Main) {
                    movingFile!!.idParent = file.id
                    fragment.updateMoving(movingFile!!)

                    movingFile = null
                }
            }
            btnNeg.setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()
        }

        private fun showPopup(view: View, position: Int) {
            val popup = PopupMenu(context, view)
            popup.setOnMenuItemClickListener {
                when (it!!.itemId) {
                    menu_rename -> {
                        GlobalScope.launch(Dispatchers.Main) {
                            fragment.renameFolder(files[position])
                        }
                        true
                    }
                    menu_move -> {
                        movingFile = files[position]
                        fragment.moveFile(movingFile!!)
                        true
                    }
                    menu_delete -> {
                        Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()
                        GlobalScope.launch(Dispatchers.Main) {
                            fragment.delete(files[position])
                            files.removeAt(position)
                            this@RecyclerAdapter.notifyDataSetChanged()
                        }
                        true
                    }
                    else -> false
                }
            }
            popup.inflate(R.menu.popup_menu)
            val menuItem = popup.menu.findItem(menu_rename)
            menuItem.isVisible = files[position].isFolder
            popup.show()
        }
    }

}