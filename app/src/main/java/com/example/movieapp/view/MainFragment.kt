package com.example.movieapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.model.Film
import com.example.movieapp.viewmodel.AppState
import com.example.movieapp.viewmodel.MainViewModel

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var recyclerView: RecyclerView
    private var adapter = RecyclerViewCategoriesAdapter(object : OnItemViewClickListener {
        override fun onItemViewClick(film: Film) {
            val manager = activity?.supportFragmentManager
            if (manager != null) {
                manager.beginTransaction()
                    .replace(R.id.container, DetailsFragment.newInstance(film))
                    .addToBackStack("")
                    .commitAllowingStateLoss()
            }
        }

    })


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.item_view_categories, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initList(view)
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initList(view: View) {
        recyclerView = view.findViewById(R.id.category_rv)
        recyclerView.adapter = adapter
        recyclerView.layoutManager =
            LinearLayoutManager(context)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getLiveData().observe(viewLifecycleOwner, { renderData(it) })
        viewModel.getDataFromLocalStorage()
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                adapter.setCategories(appState.movieData)
                adapter.notifyDataSetChanged()
            }
            is AppState.Error -> {
                adapter.setCategories(mapOf())
                adapter.notifyDataSetChanged()
            }
            is AppState.Loading -> {

            }
        }
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(film: Film)
    }
}