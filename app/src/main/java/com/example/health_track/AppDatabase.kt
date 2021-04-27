package com.example.health_track

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Aktiv::class),version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun aktivDao() : AktivDao

}