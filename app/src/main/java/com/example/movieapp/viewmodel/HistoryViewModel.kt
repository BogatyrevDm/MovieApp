package com.example.movieapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieapp.app.App.Companion.getDatabaseDAO
import com.example.movieapp.app.HistoryAppState
import com.example.movieapp.repository.LocalRepository
import com.example.movieapp.repository.LocalRepositoryImpl

class HistoryViewModel(
    val historyLiveData: MutableLiveData<HistoryAppState> = MutableLiveData(),
    private val historyRepository: LocalRepository = LocalRepositoryImpl(getDatabaseDAO())
) : ViewModel() {
    fun getAllHistory() {
        Thread {
            historyLiveData.postValue(HistoryAppState.Loading)
            historyLiveData.postValue(HistoryAppState.Success(historyRepository.getAllHistory()))
        }.start()
    }

    fun clearAllHistory() {
        Thread {
            historyLiveData.postValue(HistoryAppState.Loading)
            historyLiveData.postValue(HistoryAppState.Success(historyRepository.clearHistory()))
        }.start()
    }
}