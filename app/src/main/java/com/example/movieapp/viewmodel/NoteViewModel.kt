package com.example.movieapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieapp.app.App.Companion.getDatabaseDAO
import com.example.movieapp.app.NoteAppState
import com.example.movieapp.model.FilmSummary
import com.example.movieapp.repository.LocalRepository
import com.example.movieapp.repository.LocalRepositoryImpl

class NoteViewModel(
    val noteLiveData: MutableLiveData<NoteAppState> = MutableLiveData(),
    private val noteRepository: LocalRepository = LocalRepositoryImpl(getDatabaseDAO())
) : ViewModel() {
    fun getNote(id: Long) {
        Thread {
            noteLiveData.postValue(NoteAppState.Loading)
            noteLiveData.postValue(NoteAppState.Success(noteRepository.getNoteEntity(id)))
        }.start()
    }

    fun setNote(filmSummary: FilmSummary) {
        Thread {
            noteRepository.saveNoteEntity(filmSummary)
        }.start()
    }
}