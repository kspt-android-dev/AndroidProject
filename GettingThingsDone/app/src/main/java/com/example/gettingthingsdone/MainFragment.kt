package com.example.gettingthingsdone

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gettingthingsdone.db.entity.File
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainFragment : Fragment() {

    private lateinit var mainActivity: MainActivity
    private var isOpen = false


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    private lateinit var adapter: RecyclerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mainActivity = activity as MainActivity
        adapter = RecyclerAdapter(this)
        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(context)
        GlobalScope.launch(Dispatchers.Main) {
            val files = mainActivity.getAllByParent()
            for (file in files)
                Log.i(
                    "MY_TAG", "PARENT !!! " +
                            "id = " + file.id.toString() + " text = " + file.text.toString() + " parentId = " + file.idParent
                )
            adapter.setFiles(files)
        }
        fab()
    }

    private fun fab() {
        val fabOpen = AnimationUtils.loadAnimation(context, R.anim.fab_open)
        val fabClose = AnimationUtils.loadAnimation(context, R.anim.fab_close)
        val fabClockwise = AnimationUtils.loadAnimation(context, R.anim.rotate_clockwise)
        val fabAntiClockwise = AnimationUtils.loadAnimation(context, R.anim.rotate_anticlockwise)
        fab_add.setOnClickListener {
            isOpen = if (isOpen) {
                fab_add.startAnimation(fabAntiClockwise)
                fab_create_folder.startAnimation(fabClose)
                fab_create_note.startAnimation(fabClose)
                fab_create_folder.visibility
                fab_create_note.visibility
                false
            } else {
                fab_add.startAnimation(fabClockwise)
                fab_create_folder.startAnimation(fabOpen)
                fab_create_note.startAnimation(fabOpen)
                fab_create_folder.visibility
                fab_create_note.visibility
                true
            }
        }
        fab_create_note.setOnClickListener {
            openNote(null)
        }
        fab_create_folder.setOnClickListener {
            showDialog()
        }
    }

    fun openNote(file: File?) {
        val noteFragment = NoteFragment()
        noteFragment.setNote(file)
        fragmentManager!!.beginTransaction()
            .replace(R.id.container, noteFragment)
            .addToBackStack("note_fragment")
            .commit()
    }

    fun openFolder(file: File) {
        GlobalScope.launch(Dispatchers.Main) {
            mainActivity.root = file.id
            val files = mainActivity.getAllByParent()
            for (f in files)
                Log.i(
                    "MY_TAG", "subFolder " +
                            "id = " + f.id.toString() + " text = " + f.text.toString() + " parentId = " + f.idParent
                )
            adapter.setFiles(files)

        }
    }

    private fun showDialog() {
        val builder = AlertDialog.Builder(context)
        val dialogView = layoutInflater.inflate(R.layout.create_folder_dialog, null)
        builder.setView(dialogView)
        val btnPos = dialogView.findViewById<Button>(R.id.dialog_positive_btn)
        val btnNeg = dialogView.findViewById<Button>(R.id.dialog_negative_btn)
        val nameFolder = dialogView.findViewById<EditText>(R.id.folder_name_et)
        val dialog = builder.create()
        btnPos.setOnClickListener {
            dialog.cancel()
            val name = nameFolder.text.toString()
            if (name.isNotEmpty()) {
                GlobalScope.launch(Dispatchers.Main) {
                    val note = File()
                    note.text = name
                    note.idParent = mainActivity.root
                    note.isFolder = true
                    note.timeCreating = System.currentTimeMillis()
                    mainActivity.insertNote(note)
                    val files = mainActivity.getAllByParent()
                    adapter.setFiles(files)
                }
            }
        }
        btnNeg.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    fun onBackPressed() {
        GlobalScope.launch(Dispatchers.Main) {
            val parent = mainActivity.getParentByChild()
            mainActivity.root = parent.first().idParent
            val files =  mainActivity.getAllByParent()
            adapter.setFiles(files)
        }
    }
}