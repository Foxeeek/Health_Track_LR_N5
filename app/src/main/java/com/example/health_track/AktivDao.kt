package com.example.health_track

import androidx.room.*

@Dao
interface AktivDao {
    @Query("SELECT * from aktiv"  )
    fun getAll(): List<Aktiv>

    @Insert
    fun insertAll(vararg aktiv: Aktiv)

    @Delete
    fun delete(aktiv: Aktiv)

    @Update
    fun update(vararg aktiv: Aktiv)
}