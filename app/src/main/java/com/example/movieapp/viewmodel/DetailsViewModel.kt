package com.example.movieapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieapp.model.FilmDTO
import com.example.movieapp.repository.DetailsRepositoryImpl
import com.example.movieapp.repository.RemoteDataSource
import com.example.movieapp.utils.convertDTOToModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val SERVER_ERROR = "Ошибка сервера"
private const val REQUEST_ERROR = "Ошибка запроса на сервер"
private const val CORRUPTED_DATA = "Неполные данные"

class DetailsViewModel(

    val detailsLiveData: MutableLiveData<DetailsAppState> = MutableLiveData(),
    private val detailsRepositoryImpl: DetailsRepositoryImpl = DetailsRepositoryImpl(
        RemoteDataSource()
    )
) : ViewModel() {
    fun getFilmFromRemoteSourse(filmId: String) {
        detailsLiveData.value = DetailsAppState.Loading
        detailsRepositoryImpl.getFilmDetailsFromServer(filmId, callback)
    }

    private val callback = object : Callback<FilmDTO> {
        override fun onResponse(call: Call<FilmDTO>, response: Response<FilmDTO>) {
            val serverResponse: FilmDTO? = response.body()
            detailsLiveData.postValue(
                if (response.isSuccessful && serverResponse != null) {
                    checkResponse(serverResponse)
                } else {
                    DetailsAppState.Error(Throwable(SERVER_ERROR))
                }
            )
        }

        override fun onFailure(call: Call<FilmDTO>, t: Throwable) {
            detailsLiveData.postValue(
                DetailsAppState.Error(Throwable(t.message ?: REQUEST_ERROR))
            )
        }

        private fun checkResponse(serverResponse: FilmDTO): DetailsAppState {
            return if (serverResponse.title == null || serverResponse.original_title == null) {
                DetailsAppState.Error(Throwable(CORRUPTED_DATA))
            } else {
                DetailsAppState.Success(convertDTOToModel(serverResponse))
            }
        }
    }
}