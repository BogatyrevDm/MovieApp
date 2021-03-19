package com.example.movieapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieapp.model.Categories
import com.example.movieapp.model.FilmSummaryDTO
import com.example.movieapp.model.FilmsListDTO
import com.example.movieapp.repository.RemoteDataSource
import com.example.movieapp.repository.Repository
import com.example.movieapp.repository.RepositoryImpl
import com.example.movieapp.utils.convertListDTOToModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val SERVER_ERROR = "Ошибка сервера"
private const val REQUEST_ERROR = "Ошибка запроса на сервер"

class MainViewModel(
    val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val repositoryIml: Repository = RepositoryImpl(RemoteDataSource()),
    private val appStateSuccess: AppState.Success = AppState.Success()

) : ViewModel() {

    fun getDataFromRemoteSourse(showAdultContent:Boolean) = getFilmsFromRemoteStorage(showAdultContent)

    private fun getFilmsFromRemoteStorage(showAdultContent:Boolean) {
        liveDataToObserve.value = AppState.Loading
        Categories.values().forEach {
            repositoryIml.getFilmsFromServer(it,showAdultContent, callback)
        }
    }

    private val callback = object : Callback<FilmsListDTO> {
        override fun onResponse(call: Call<FilmsListDTO>, response: Response<FilmsListDTO>) {
            val serverResponse: FilmsListDTO? = response.body()
            val categoryTag = response.raw().request().tag(Categories::class.java)
            val showAdultContent = response.raw().request().tag(String::class.java).toBoolean()
            if (categoryTag != null && showAdultContent != null) {
                liveDataToObserve.postValue(
                    if (response.isSuccessful && serverResponse != null) {
                        checkResponse(serverResponse.results, categoryTag, showAdultContent)
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
            categoryTag: Categories,
            showAdultContent: Boolean
        ): AppState {
            val categoryList = convertListDTOToModel(serverResponse, showAdultContent)
             when (categoryTag) {
                Categories.NOWPLAYING -> {
                    appStateSuccess.NowPlayingData = categoryList
                }
                Categories.POPULAR -> {
                    appStateSuccess.PopularData = categoryList
                }
                Categories.TOPRATED -> {
                    appStateSuccess.TopRatedData = categoryList
                }
                Categories.UPCOMING -> {
                    appStateSuccess.UpComingData = categoryList
                }
            }
            return appStateSuccess
        }
    }

}