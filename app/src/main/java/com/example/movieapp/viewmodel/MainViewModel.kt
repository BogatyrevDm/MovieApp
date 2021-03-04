package com.example.movieapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieapp.model.Repository
import com.example.movieapp.model.RepositoryImpl

class MainViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val repositoryIml: Repository = RepositoryImpl()
) : ViewModel() {
    fun getLiveData() = liveDataToObserve
    fun getDataFromLocalStorage() = getFilmsFromLocalStorage()

    private fun getFilmsFromLocalStorage() {
        liveDataToObserve.value = AppState.Loading
        Thread {
            Thread.sleep(1000)
            liveDataToObserve.postValue(AppState.Success(repositoryIml.getFilmsFromLocalStorage()))
        }.start()
    }


}