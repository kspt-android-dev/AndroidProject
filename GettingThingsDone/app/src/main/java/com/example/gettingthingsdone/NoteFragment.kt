package com.example.gettingthingsdone

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.gettingthingsdone.db.entity.File
import kotlinx.android.synthetic.main.note_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class NoteFragment : Fragment() {

    var file: File? = null

    private lateinit var mainActivity: MainActivity
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.note_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        ic_done.setOnClickListener {
            close(note_edit_text.text.toString(), file)
        }
        ic_arrow_back.setOnClickListener {
            close("", file)
        }
        mainActivity = activity as MainActivity
        if (file != null) note_edit_text.setText(file!!.text)
        setTextWatcher()
    }

    private fun close(text: String, file: File?) {
        GlobalScope.launch(Dispatchers.Main) {
            if (file != null) {
                if (text.isEmpty())
                    mainActivity.deleteNote(file)
                else
                    file.text = text
                mainActivity.updateNote(file)

            } else if (text.isNotEmpty()) {
                val note = File()
                note.text = text
                note.idParent = mainActivity.root
                note.isFolder = false
                note.timeCreating = System.currentTimeMillis()
                mainActivity.insertNote(note)
            }

            val files = mainActivity.getAll()
            fragmentManager!!.popBackStack("note_fragment", FragmentManager.POP_BACK_STACK_INCLUSIVE)
            for (i in files)
                Log.i(
                    "MY_TAG",
                    "id = " + i.id.toString() + " text = " + i.text.toString() + "parentId = " + i.idParent
                )
        }
    }

    private fun setTextWatcher() {
        note_edit_text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if ((s.toString().isNotEmpty() && file == null) || (file != null && s.toString() != file!!.text)) {
                    ic_done.visibility = View.VISIBLE
                } else {
                    ic_done.visibility = View.GONE
                }
            }
        })
    }

    fun setNote(file: File?) {
        this.file = file
    }

    fun onBackPressed(): Boolean {
        return if (isDetached){
            false
        }else{
            showCancelDialog()
            true
        }

    }

    private fun showCancelDialog(){
        val builder = AlertDialog.Builder(context)
        val dialogView = layoutInflater.inflate(R.layout.custom_card_view, null)
        builder.setView(dialogView)
        val btnPos = dialogView.findViewById<Button>(R.id.dialog_positive_btn)
        val btnNeg = dialogView.findViewById<Button>(R.id.dialog_negative_btn)
        val dialog = builder.create()
        btnPos.setOnClickListener {
            dialog.cancel()
            close("", file)
        }
        btnNeg.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }
}