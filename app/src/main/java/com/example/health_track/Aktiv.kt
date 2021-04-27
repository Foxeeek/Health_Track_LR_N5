package com.example.health_track

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "aktiv")
data class Aktiv(
    @PrimaryKey(autoGenerate = true) val id:Int, val label:String,val amount:Double,val description: String) {
}