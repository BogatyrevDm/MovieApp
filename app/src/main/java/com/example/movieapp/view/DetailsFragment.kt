package com.example.movieapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.movieapp.databinding.FragmentDetailsBinding
import com.example.movieapp.model.Film


class DetailsFragment : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val film = arguments?.getParcelable<Film>(BUNDLE_EXTRA)
        if (film != null) {
            binding.titleDetails.text = film.title
            binding.originalTitleDetails.text = film.originalTitle
            binding.runtimeDetails.text = film.runtime.toString()
            binding.genresDetails.text = film.genres
            binding.budgetDetails.text = film.budget.toString()
            binding.revenueDetails.text = film.revenue.toString()
            binding.releaseDateDetails.text = film.releaseDate
            binding.overviewDetails.text = film.overview
        }
    }

    companion object {
        const val BUNDLE_EXTRA = "film"
        fun newInstance(film: Film): DetailsFragment {
            val fragment = DetailsFragment()
            val bundle = Bundle()
            bundle.putParcelable(BUNDLE_EXTRA, film)
            fragment.arguments = bundle
            return fragment

        }
    }
}