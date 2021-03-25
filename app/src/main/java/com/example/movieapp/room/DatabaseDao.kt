package com.example.movieapp.room

import androidx.room.*

@Dao
interface DatabaseDao {
    @Query("SELECT * FROM HistoryEntity")
    fun allHistory(): List<HistoryEntity>

    @Query("DELETE FROM HistoryEntity")
    fun clearHistory()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHistory(entity: HistoryEntity)

    @Update
    fun updateHistory(entity: HistoryEntity)

    @Delete
    fun deleteHistory(entity: HistoryEntity)

    @Query("SELECT * FROM NoteEntity")
    fun allNotes(): List<NoteEntity>

    @Query("SELECT * FROM NoteEntity WHERE id=:id")
    fun getNoteById(id: Long): NoteEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(entity: NoteEntity)

    @Update
    fun updateNote(entity: NoteEntity)

    @Delete
    fun deleteNote(entity: NoteEntity)
}