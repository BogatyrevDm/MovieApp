package com.example.movieapp.view

import android.app.IntentService
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.movieapp.BuildConfig
import com.example.movieapp.model.FilmDTO
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

const val FILM_ID = "FilmId"
private const val REQUEST_GET = "GET"
private const val REQUEST_TIMEOUT = 10000

class DetailsService(name: String = "DetailService") : IntentService(name) {
    private val broadcastIntent = Intent(DETAILS_INTENT_FILTER)

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onHandleIntent(intent: Intent?) {
        if (intent == null) {
            onEmptyIntent()
        } else {
            val filmID = intent.getIntExtra(FILM_ID, 0)
            if (filmID == 0) {
                onEmptyData()
            } else {
                loadFilm(filmID.toString())
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.N)
    private fun loadFilm(filmID: String) {
        try {
            val uri =
                URL("https://api.themoviedb.org/3/movie/${filmID}?api_key=${BuildConfig.MOVIEDB_API_KEY}&language=en-US")


            lateinit var urlConnection: HttpsURLConnection
            try {
                urlConnection = uri.openConnection() as HttpsURLConnection
                urlConnection.requestMethod = REQUEST_GET
                urlConnection.readTimeout = REQUEST_TIMEOUT
                val bufferedReader =
                    BufferedReader(InputStreamReader(urlConnection.inputStream))
                val filmDTO: FilmDTO =
                    Gson().fromJson(getLines(bufferedReader), FilmDTO::class.java)
                onResponse(filmDTO)
            } catch (e: Exception) {
                onErrorRequest(e.message ?: "Empry error")
            } finally {
                urlConnection.disconnect()
            }

        } catch (e: MalformedURLException) {
            onMalformedURL()
        }

    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
    }

    private fun onResponse(filmDTO: FilmDTO?) {
        if (filmDTO == null) {
            onEmptyResponse()
        } else {
            onSuccessResponse(filmDTO)
        }
    }

    private fun onSuccessResponse(filmDTO: FilmDTO) {
        putLoadResult(DETAILS_RESPONSE_SUCCESS_EXTRA)
        broadcastIntent.putExtra(DETAILS_FILM_EXTRA, filmDTO)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }


    private fun onMalformedURL() {
        putLoadResult(DETAILS_URL_MALFORMED_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onErrorRequest(message: String) {
        putLoadResult(DETAILS_REQUEST_ERROR_EXTRA)
        broadcastIntent.putExtra(DETAILS_REQUEST_ERROR_MESSAGE_EXTRA, message)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }


    private fun onEmptyResponse() {
        putLoadResult(DETAILS_RESPONSE_EMPTY_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onEmptyIntent() {
        putLoadResult(DETAILS_INTENT_EMPTY_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onEmptyData() {
        putLoadResult(DETAILS_DATA_EMPTY_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun putLoadResult(result: String) {
        broadcastIntent.putExtra(DETAILS_LOAD_RESULT_EXTRA, result)
    }

}