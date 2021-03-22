package com.example.movieapp.view

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.movieapp.app.NoteAppState
import com.example.movieapp.databinding.FragmentNoteBinding
import com.example.movieapp.model.FilmSummary
import com.example.movieapp.utils.ShowSnackBar
import com.example.movieapp.viewmodel.NoteViewModel


class NoteFragment : Fragment() {
    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding!!
    private lateinit var filmSummary: FilmSummary
    private val viewModel: NoteViewModel by lazy {
        ViewModelProvider(this).get(NoteViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        filmSummary = arguments?.getParcelable(BUNDLE_EXTRA) ?: FilmSummary()
        viewModel.noteLiveData.observe(viewLifecycleOwner, {
            renderData(it)
        })
        viewModel.getNote(filmSummary.id.toLong())
        initView()
    }

    private fun initView() {
        binding.noteOkButton.setOnClickListener {
            filmSummary.note = binding.noteEt.text.toString()
            viewModel.setNote(filmSummary)
            activity?.supportFragmentManager?.popBackStack()
        }
        binding.noteOkCancel.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
    }

    private fun renderData(noteAppState: NoteAppState) {
        when (noteAppState) {
            is NoteAppState.Success -> {
                displayFilm(noteAppState.filmData)
            }
            is NoteAppState.Error -> {
                binding.mainContainerNote.ShowSnackBar(
                    "Error", "Reload",
                    {
                        viewModel.getNote(filmSummary.id.toLong())
                    })
            }
        }
    }

    private fun displayFilm(filmSummary: FilmSummary?) {
        binding.noteEt.setText(filmSummary?.note)
    }

    companion object {
        private const val BUNDLE_EXTRA = "FILM_ID"
        fun newInstance(filmSummary: FilmSummary): NoteFragment {
            val fragment = NoteFragment()
            val bundle = Bundle()
            bundle.putParcelable(BUNDLE_EXTRA, filmSummary)
            fragment.arguments = bundle
            return fragment
        }
    }
}