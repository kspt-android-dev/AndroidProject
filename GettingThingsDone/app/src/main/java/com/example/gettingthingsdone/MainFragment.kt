package com.example.gettingthingsdone

import android.app.AlertDialog
import android.os.Bundle
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
            val files = mainActivity.sqlEngine.getAllByParent(mainActivity.rootId)
            val parent = mainActivity.sqlEngine.getFileById(mainActivity.rootId)
            if (parent.isEmpty()) toolbar_text.text = getString(R.string.app_name)
            else toolbar_text.text = parent.first().text
            adapter.setFiles(files)
        }
        ic_arrow_back.setOnClickListener {
            onBackPressed()
        }
        fab()
        if (mainActivity.rootId != 0L)
            ic_arrow_back.visibility = View.VISIBLE
        else
            ic_arrow_back.visibility = View.GONE

    }

    fun showCall() {
        empty_recycler_text_view.visibility = View.VISIBLE
    }

    fun hideCall() {
        empty_recycler_text_view.visibility = View.GONE
    }

    private fun fab() {
        fab_add.setOnClickListener {
            isOpen = if (isOpen) {
                closeFab()
                false
            } else {
                openFab()
                true
            }
        }
        fab_create_note.setOnClickListener {
            closeIfPossible()
            mainActivity.createNoteFragment(null)
        }
        fab_create_folder.setOnClickListener {
            closeIfPossible()
            showDialog(null)
        }
    }

    private fun closeFab() {
        val fabClose = AnimationUtils.loadAnimation(context, R.anim.fab_close)
        val fabAntiClockwise = AnimationUtils.loadAnimation(context, R.anim.rotate_anticlockwise)
        fab_add.startAnimation(fabAntiClockwise)
        fab_create_folder.startAnimation(fabClose)
        fab_create_note.startAnimation(fabClose)
    }

    private fun openFab() {
        val fabClockwise = AnimationUtils.loadAnimation(context, R.anim.rotate_clockwise)
        val fabOpen = AnimationUtils.loadAnimation(context, R.anim.fab_open)
        fab_add.startAnimation(fabClockwise)
        fab_create_folder.startAnimation(fabOpen)
        fab_create_note.startAnimation(fabOpen)

    }

    fun openFolder(file: File) {
        GlobalScope.launch(Dispatchers.Main) {
            mainActivity.rootId = file.id
            val files = mainActivity.sqlEngine.getAllByParent(mainActivity.rootId)
            ic_arrow_back.visibility = View.VISIBLE
            adapter.setFiles(files)
            closeIfPossible()
            toolbar_text.text = file.text
        }
    }

    private fun showDialog(file: File?) {
        val builder = AlertDialog.Builder(context)
        val dialogView = View.inflate(context, R.layout.create_folder_dialog, null)
        builder.setView(dialogView)
        val btnPos = dialogView.findViewById<Button>(R.id.dialog_positive_btn)
        val btnNeg = dialogView.findViewById<Button>(R.id.dialog_negative_btn)
        val nameFolder = dialogView.findViewById<EditText>(R.id.folder_name_et)

        val dialog = builder.create()
        if (file != null) {
            nameFolder.setText(file.text)
        }
        btnPos.setOnClickListener {
            dialog.cancel()
            val name = nameFolder.text.toString()
            if (name.isNotEmpty()) {
                GlobalScope.launch(Dispatchers.Main) {
                    val engine = mainActivity.sqlEngine
                    val root = mainActivity.rootId
                    if (file == null) {
                        val note = File()
                        note.text = name
                        note.idParent = root
                        note.isFolder = true
                        note.timeCreating = System.currentTimeMillis()
                        engine.insertFile(note)
                        val files = engine.getAllByParent(root)
                        adapter.setFiles(files)
                        closeIfPossible()
                    } else {
                        file.text = name
                        mainActivity.sqlEngine.updateFile(file)
                        adapter.notifyFile(file)
                    }
                }
            }
        }
        btnNeg.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    fun hideBackButton() {
        ic_arrow_back.visibility = View.GONE
    }

    fun onBackPressed(): Boolean {
        adapter.movingFile = null
        val root = mainActivity.rootId
        val engine = mainActivity.sqlEngine
        return if (root != 0L) {
            GlobalScope.launch(Dispatchers.Main) {
                val here = engine.getFileById(root)
                mainActivity.rootId = here.first().idParent
                val parent = engine.getFileById(mainActivity.rootId)
                if (mainActivity.rootId == 0L) {
                    toolbar_text.text = getString(R.string.app_name)
                    hideBackButton()
                } else {
                    toolbar_text.text = parent.first().text
                }
                val files = engine.getAllByParent(mainActivity.rootId)
                closeIfPossible()
                adapter.setFiles(files)
            }
            true
        } else {
            false
        }
    }

    fun openNote(file: File) {
        closeIfPossible()
        mainActivity.createNoteFragment(file)
    }

    suspend fun delete(file: File) {
        if (!file.isFolder)
            mainActivity.sqlEngine.deleteFile(file)
        else {
            deleteFolder(file)
        }
    }

    private suspend fun deleteFolder(file: File) {
        val list = mainActivity.sqlEngine.getAllByParent(file.id)
        for (item in list) {
            if (!item.isFolder)
                mainActivity.sqlEngine.deleteFile(item)
            else {
                deleteFolder(item)
            }
        }

        mainActivity.sqlEngine.deleteFile(file)

    }

    fun renameFolder(file: File) {
        showDialog(file)
    }

    fun moveFile(file: File) {
        GlobalScope.launch(Dispatchers.Main) {
            listSubFolder = mutableSetOf()
            listSubFolder.add(findFolder(file))
            val listAllFolder = mainActivity.sqlEngine.getAll()
            val listFolders = listAllFolder.filter { it.isFolder }
            val filteredList = listFolders.filter { !listSubFolder.contains(it.id) }
            val rootFile = File()
            rootFile.id = 0
            rootFile.idParent = 0
            rootFile.text = "."
            rootFile.isFolder = true
            val list = filteredList.toMutableSet()
            list.add(rootFile)
            closeIfPossible()
            adapter.setFiles(list.toList())
        }
    }

    private lateinit var listSubFolder: MutableSet<Long>

    private suspend fun findFolder(file: File): Long {
        val list = mainActivity.sqlEngine.getAllByParent(file.id)
        for (item in list) {
            if (item.isFolder) {
                listSubFolder.add(findFolder(item))
            }
        }
        return file.id
    }

    suspend fun updateMoving(movingFile: File) {
        mainActivity.sqlEngine.updateFile(movingFile)
        val files = mainActivity.sqlEngine.getAllByParent(mainActivity.rootId)
        closeIfPossible()
        ic_arrow_back.visibility = View.VISIBLE
        adapter.setFiles(files)
    }

    fun closeIfPossible() {
        if (isOpen) {
            closeFab()
            isOpen = false
        }
    }
}