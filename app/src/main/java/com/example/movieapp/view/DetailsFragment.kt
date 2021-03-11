package com.example.movieapp.view

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.movieapp.databinding.FragmentDetailsBinding
import com.example.movieapp.model.Film
import com.example.movieapp.model.FilmDTO
import com.google.android.material.snackbar.Snackbar


class DetailsFragment : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var filmBundle: Film
    private val onLoaderListener: FilmLoader.FilmLoaderListener =
        object : FilmLoader.FilmLoaderListener {
            override fun onLoaded(filmDTO: FilmDTO) {
                displayFilms(filmDTO)
            }

            override fun onFailed(throwable: Throwable) {
                Snackbar.make(
                    binding.root,
                    throwable.stackTraceToString(),
                    Snackbar.LENGTH_INDEFINITE
                ).show()
            }

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
        val loader = FilmLoader(onLoaderListener, filmBundle.filmSummary.id)
        loader.loadFilms()


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