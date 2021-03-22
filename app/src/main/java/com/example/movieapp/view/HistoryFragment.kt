package com.example.movieapp.view

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.app.HistoryAppState
import com.example.movieapp.databinding.FragmentHistoryBinding
import com.example.movieapp.viewmodel.HistoryViewModel
import com.google.android.material.snackbar.Snackbar

class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HistoryViewModel by lazy { ViewModelProvider(this).get(HistoryViewModel::class.java) }

    private lateinit var recyclerView: RecyclerView
    private var adapter = RecyclerViewHistoryAdapter()

    companion object {
        fun newInstance() = HistoryFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.history_fragment_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.menu_clear_history -> {
                viewModel.clearAllHistory()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initList(view)
        viewModel.historyLiveData.observe(viewLifecycleOwner, {
            renderData(it)
        })
        viewModel.getAllHistory()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun renderData(appState: HistoryAppState) {
        when (appState) {
            is HistoryAppState.Success -> {
                adapter.setData(appState.filmData)
            }
            is HistoryAppState.Error -> {
                binding.historyFragmentRootView.showSnackBarLocal(
                    getString(R.string.error),
                    getString(R.string.reload),
                    { viewModel.getAllHistory() }
                )
            }
            is HistoryAppState.Loading -> {
            }
        }
    }

    private fun View.showSnackBarLocal(
        text: String,
        actionText: String,
        action: (View) -> Unit,
        length: Int = Snackbar.LENGTH_INDEFINITE
    ) {
        Snackbar.make(this, text, length).setAction(actionText, action).show()
    }

    private fun initList(view: View) {
        recyclerView = view.findViewById(R.id.history_rv)
        recyclerView.adapter = adapter
        recyclerView.layoutManager =
            LinearLayoutManager(context)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}