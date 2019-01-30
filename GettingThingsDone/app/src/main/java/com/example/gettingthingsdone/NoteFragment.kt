package com.example.gettingthingsdone

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
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
        val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        note_edit_text.postDelayed({
            note_edit_text.requestFocus()
            imm!!.showSoftInput(note_edit_text, 0)
        }, 50)
        ic_done.setOnClickListener {
            close(note_edit_text.text.toString(), file, false)
        }
        ic_arrow_back.setOnClickListener {
            showCancelDialogIfPossible()
        }
        mainActivity = activity as MainActivity
        if (file != null) note_edit_text.setText(file!!.text)
        setTextWatcher()
    }

    private fun close(text: String, file: File?, isClose: Boolean) {
        GlobalScope.launch(Dispatchers.Main) {
            if (file != null) {
                if (!isClose) {
                    if (text.isEmpty())
                        mainActivity.sqlEngine.deleteFile(file)
                    else
                        file.text = text
                    mainActivity.sqlEngine.updateFile(file)
                }

            } else if (text.isNotEmpty() && !isClose) {
                val note = File()
                note.text = text
                note.idParent = mainActivity.rootId
                note.isFolder = false
                note.timeCreating = System.currentTimeMillis()
                mainActivity.sqlEngine.insertFile(note)
            }
            fragmentManager!!.popBackStack("note_fragment", FragmentManager.POP_BACK_STACK_INCLUSIVE)

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
        return if (isDetached || context == null) {
            false
        } else {
            showCancelDialogIfPossible()
            true
        }

    }

    private fun showCancelDialogIfPossible() {
        if (file!= null){
            if (note_edit_text.text.toString() != file!!.text){
                showCancelDialog()
            }
            else{
                close(note_edit_text.text.toString(), file, true)
            }
        }else{
            if(note_edit_text.text.isNotEmpty()){
                showCancelDialog()
            }
            else{
                close(note_edit_text.text.toString(), file, true)
            }
        }

    }

    private fun showCancelDialog(){
        val builder = AlertDialog.Builder(context)
        val dialogView = View.inflate(context, R.layout.custom_cancel_dialog, null)
        builder.setView(dialogView)
        val btnPos = dialogView.findViewById<Button>(R.id.dialog_pos_btn)
        val btnNeg = dialogView.findViewById<Button>(R.id.dialog_neg_btn)
        val dialog = builder.create()
        btnPos.setOnClickListener {
            dialog.cancel()
            close(note_edit_text.text.toString(), file, true)
        }
        btnNeg.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }


}