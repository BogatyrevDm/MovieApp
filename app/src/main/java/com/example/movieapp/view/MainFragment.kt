package com.example.movieapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.model.Film
import com.example.movieapp.viewmodel.AppState
import com.example.movieapp.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }
    private lateinit var recyclerView: RecyclerView
    private var adapter = RecyclerViewCategoriesAdapter(object : OnItemViewClickListener {
        override fun onItemViewClick(film: Film) {
            activity?.supportFragmentManager?.apply {
                beginTransaction()
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
        viewModel.liveDataToObserve.observe(viewLifecycleOwner, {
            renderData(it)
        })
        viewModel.getDataFromRemoteSourse()
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                adapter.setCategories(appState)
                adapter.notifyDataSetChanged()
            }
            is AppState.Error -> {
                view?.findViewById<LinearLayout>(R.id.mainFragmentRootView)?.showSnackBar(
                    getString(R.string.error),
                    getString(R.string.reload),
                    { viewModel.getDataFromRemoteSourse() }
                )
            }
            is AppState.Loading -> {
            }
        }
    }

    private fun View.showSnackBar(
        text: String,
        actionText: String,
        action: (View) -> Unit,
        length: Int = Snackbar.LENGTH_INDEFINITE
    ) {
        Snackbar.make(this, text, length).setAction(actionText, action).show()
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(film: Film)
    }
}