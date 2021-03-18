package com.example.movieapp.view

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import com.example.movieapp.databinding.FragmentDetailsBinding
import com.example.movieapp.model.Film
import com.example.movieapp.utils.ShowSnackBar
import com.example.movieapp.viewmodel.DetailsAppState
import com.example.movieapp.viewmodel.DetailsViewModel
import com.squareup.picasso.Picasso


class DetailsFragment : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var filmBundle: Film
    private val viewModel: DetailsViewModel by lazy {
        ViewModelProvider(this).get(DetailsViewModel::class.java)
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
        viewModel.detailsLiveData.observe(viewLifecycleOwner, Observer { renderData(it) })
        viewModel.getFilmFromRemoteSourse(filmBundle.filmSummary.id.toString())
    }

    private fun renderData(detailsAppState: DetailsAppState) {
        when (detailsAppState) {
            is DetailsAppState.Success -> {
                displayFilms(detailsAppState.movieData)
            }
            is DetailsAppState.Error -> {
                binding.mainContainerDetails.ShowSnackBar("Error", "Reload",
                    { viewModel.getFilmFromRemoteSourse(filmBundle.filmSummary.id.toString()) })
            }
        }
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

    private fun displayFilms(film: Film) {
        binding.titleDetails.text = film.filmSummary.title
        binding.originalTitleDetails.text = film.originalTitle
        binding.runtimeDetails.text = film.runtime.toString()
        binding.averageVoteDetails.text = film.filmSummary.averageVote.toString()
        binding.genresDetails.text = film.genres
        binding.budgetDetails.text = film.budget.toString()
        binding.revenueDetails.text = film.revenue.toString()
        binding.releaseDateDetails.text = film.filmSummary.releaseDate
        binding.overviewDetails.text = film.overview
        // Picasso.get().load("https://image.tmdb.org/t/p/original/${film.posterPath}").into(binding.posterDetails)
        binding.posterDetails.load("https://image.tmdb.org/t/p/original/${film.filmSummary.posterPath}")
    }
}