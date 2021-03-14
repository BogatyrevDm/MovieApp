package com.example.movieapp.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.movieapp.databinding.FragmentDetailsBinding
import com.example.movieapp.model.Film
import com.example.movieapp.model.FilmDTO
import com.google.android.material.snackbar.Snackbar

const val DETAILS_INTENT_FILTER = "DETAILS INTENT FILTER"
const val DETAILS_LOAD_RESULT_EXTRA = "LOAD RESULT"
const val DETAILS_INTENT_EMPTY_EXTRA = "INTENT IS EMPTY"
const val DETAILS_DATA_EMPTY_EXTRA = "DATA IS EMPTY"
const val DETAILS_RESPONSE_EMPTY_EXTRA = "RESPONSE IS EMPTY"
const val DETAILS_REQUEST_ERROR_EXTRA = "REQUEST ERROR"
const val DETAILS_REQUEST_ERROR_MESSAGE_EXTRA = "REQUEST MESSAGE ERROR"
const val DETAILS_URL_MALFORMED_EXTRA = "URL MALFORMED"
const val DETAILS_RESPONSE_SUCCESS_EXTRA = "RESPONSE SUCCESS"
const val DETAILS_FILM_EXTRA = "FILM EXTRA"
private const val PROCESS_ERROR = "Обработка ошибки"

class DetailsFragment : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var filmBundle: Film
    private val loadResultReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            when (intent.getStringExtra(DETAILS_LOAD_RESULT_EXTRA)) {
                DETAILS_INTENT_EMPTY_EXTRA -> processError(DETAILS_INTENT_EMPTY_EXTRA)
                DETAILS_DATA_EMPTY_EXTRA -> processError(DETAILS_DATA_EMPTY_EXTRA)
                DETAILS_RESPONSE_EMPTY_EXTRA -> processError(DETAILS_RESPONSE_EMPTY_EXTRA)
                DETAILS_REQUEST_ERROR_EXTRA -> processError(DETAILS_REQUEST_ERROR_EXTRA)
                DETAILS_REQUEST_ERROR_MESSAGE_EXTRA -> processError(DETAILS_REQUEST_ERROR_MESSAGE_EXTRA)
                DETAILS_URL_MALFORMED_EXTRA -> processError(DETAILS_URL_MALFORMED_EXTRA)
                DETAILS_RESPONSE_SUCCESS_EXTRA -> intent.getParcelableExtra<FilmDTO>(
                    DETAILS_FILM_EXTRA
                )?.let {
                    displayFilms(
                        it
                    )
                }
            }
        }

    }

    private fun processError(message:String) {
        Snackbar.make(
            binding.root,
            message,
            Snackbar.LENGTH_INDEFINITE
        ).setAction("Reload") {
            runIntent()
        }.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(
            loadResultReceiver, IntentFilter(
                DETAILS_INTENT_FILTER
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        filmBundle = arguments?.getParcelable(BUNDLE_EXTRA) ?: Film()
        runIntent()
    }
private fun runIntent(){
    requireContext().apply {
        startService(Intent(this, DetailsService::class.java).apply {
            putExtra(FILM_ID, filmBundle.filmSummary.id)
        })
    }
}
    override fun onDestroy() {
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(loadResultReceiver)
        super.onDestroy()
    }

    companion object {
        private const val BUNDLE_EXTRA = "film"
        fun newInstance(film: Film): DetailsFragment {
            val fragment = DetailsFragment()
            val bundle = Bundle()
            bundle.putParcelable(BUNDLE_EXTRA, film)
            fragment.arguments = bundle
            return fragment

        }
    }

    private fun displayFilms(filmDTO: FilmDTO) {
        binding.titleDetails.text = filmBundle.filmSummary.title
        binding.originalTitleDetails.text = filmBundle.originalTitle
        binding.runtimeDetails.text = filmDTO.runtime.toString()
        binding.genresDetails.text = filmBundle.genres
        binding.budgetDetails.text = filmDTO.budget.toString()
        binding.revenueDetails.text = filmDTO.revenue.toString()
        binding.releaseDateDetails.text = filmBundle.filmSummary.releaseDate
        binding.overviewDetails.text = filmDTO.overview
    }
}