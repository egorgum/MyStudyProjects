package com.blblblbl.myapplication.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photos_db")
data class Photo(
    @PrimaryKey
    @ColumnInfo(name = "uri")
    val uri:String,
    @ColumnInfo(name = "date")
    val date:String
) {
    override fun toString(): String {
        return "uri:$uri date:$date"
    }
}