package com.example.gettingthingsdone

import com.example.gettingthingsdone.db.entity.File
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SQLEngine(val app : GTDApplication) {
    suspend fun insertFile(note: File) = withContext(Dispatchers.IO) {
        app.db.fileDao().insert(note)
    }

    suspend fun deleteFile(note: File) = withContext(Dispatchers.IO) {
        app.db.fileDao().delete(note)
    }

    suspend fun updateFile(note: File) = withContext(Dispatchers.IO) {
        app.db.fileDao().update(note)
    }

    suspend fun getAllByParent(root: Long) = withContext(Dispatchers.IO) {
        app.db.fileDao().getAllByParent(root)
    }

    suspend fun getParentByChild(root: Long) = withContext(Dispatchers.IO) {
        app.db.fileDao().getParentByChild(root)
    }

    suspend fun getAll() = withContext(Dispatchers.IO) {
        app.db.fileDao().getAll()
    }
}
