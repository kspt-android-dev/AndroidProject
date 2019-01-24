package com.example.gettingthingsdone

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.util.Log

class CustomContentProvider : ContentProvider() {

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        Log.i("MY_CONTENT_PROVIDER","insert")
        return null
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?): Cursor? {
        Log.i("MY_CONTENT_PROVIDER","query")
        return null
    }

    override fun onCreate(): Boolean {
        Log.i("MY_CONTENT_PROVIDER","onCreate")
        return true
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        Log.i("MY_CONTENT_PROVIDER","update")
        return 0
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        Log.i("MY_CONTENT_PROVIDER","delete")
        return 0
    }

    override fun getType(uri: Uri): String? {
        Log.i("MY_CONTENT_PROVIDER","getType")
        return ""
    }
}