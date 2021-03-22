package com.example.movieapp.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = arrayOf(HistoryEntity::class, NoteEntity::class),
    version = 1,
    exportSchema = false
)
abstract class DataBase : RoomDatabase() {
    abstract fun databaseDao():DatabaseDao
}