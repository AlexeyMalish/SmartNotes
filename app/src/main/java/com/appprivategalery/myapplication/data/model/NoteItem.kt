package com.appprivategalery.myapplication.data.model

import androidx.room.PrimaryKey

data class NoteItem(
    @PrimaryKey(autoGenerate = false)
                    var id : Int,
                    var info: String?,
                    var colorText: Int?,
                    var isBold: Boolean?,
                    var underline : Boolean?,
                    var urlToMedia: String?,) : java.io.Serializable
