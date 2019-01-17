package com.example.gettingthingsdone.db.entity

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.Serializable

class Converter : Serializable {

    private var gson = Gson()

    @TypeConverter()
    fun stringToMutableSetOfLong(data: String?): MutableSet<Long> {
        return if (data == null) {
            mutableSetOf()
        } else {
            val listType = object : TypeToken<MutableSet<Long>>() {}.type
            this.gson.fromJson(data, listType)
        }
    }

    @TypeConverter
    fun mutableSetOfLongToString(owner: MutableSet<Long>?): String {
        return this.gson.toJson(owner)
    }
}