package com.example.movieapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieapp.model.Categories
import com.example.movieapp.model.FilmsListDTO
import com.example.movieapp.model.FilmSummaryDTO
import com.example.movieapp.repository.RemoteDataSource
import com.example.movieapp.repository.Repository
import com.example.movieapp.repository.RepositoryImpl
import com.example.movieapp.utils.convertListDTOToModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val SERVER_ERROR = "Ошибка сервера"
private const val REQUEST_ERROR = "Ошибка запроса на сервер"
private const val CORRUPTED_DATA = "Неполные данные"

class MainViewModel(
    val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val repositoryIml: Repository = RepositoryImpl(RemoteDataSource())
) : ViewModel() {

    fun getDataFromRemoteSourse() = getFilmsFromRemoteStorage()

    private fun getFilmsFromRemoteStorage() {
        liveDataToObserve.value = AppState.Loading
        Categories.values().forEach {
            repositoryIml.getFilmsFromServer(it, callback)
        }
    }

    private val callback = object : Callback<FilmsListDTO> {
        override fun onResponse(call: Call<FilmsListDTO>, response: Response<FilmsListDTO>) {
            val serverResponse: FilmsListDTO? = response.body()
            val categoryTag = response.raw().request().tag(Categories::class.java)
            if (categoryTag != null) {
                liveDataToObserve.postValue(
                    if (response.isSuccessful && serverResponse != null) {
                        checkResponse(serverResponse.results, categoryTag)
                    } else {
                        AppState.Error(Throwable(SERVER_ERROR))
                    }
                )
            }
        }

        override fun onFailure(call: Call<FilmsListDTO>, t: Throwable) {
            liveDataToObserve.postValue(
                AppState.Error(Throwable(t.message ?: REQUEST_ERROR))
            )
        }

        private fun checkResponse(
            serverResponse: List<FilmSummaryDTO>,
            categoryTag: Categories
        ): AppState {

            return AppState.Success(
                convertListDTOToModel(serverResponse),
                Categories.values().indexOf(categoryTag)
            )
        }
    }

}