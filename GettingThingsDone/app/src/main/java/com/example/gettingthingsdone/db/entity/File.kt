package com.example.gettingthingsdone.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class File {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    var idParent: Long = 0

    var isFolder: Boolean = true

    var text: String? = null

    var timeCreating: Long = 0
}