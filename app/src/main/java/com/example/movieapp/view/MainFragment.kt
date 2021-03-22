package com.example.movieapp.view

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.View
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.app.AppState
import com.example.movieapp.databinding.FragmentMainBinding
import com.example.movieapp.model.Film
import com.example.movieapp.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import com.example.movieapp.utils.ShowSnackBar
private const val ADULT_CONTENT_KEY = "ADULT_CONTENT"

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

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
    private var showAdultContent = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        _binding = FragmentMainBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initList()
        readAdultContentKey()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun readAdultContentKey() {
        activity?.let {
            showAdultContent =
                it.getPreferences(Context.MODE_PRIVATE).getBoolean(ADULT_CONTENT_KEY, false)
        }
    }

    private fun initList() {
        recyclerView = binding.categoriesRv
        recyclerView.adapter = adapter
        recyclerView.layoutManager =
            LinearLayoutManager(context)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.liveDataToObserve.observe(viewLifecycleOwner, {
            renderData(it)
        })
        viewModel.getDataFromRemoteSourse(showAdultContent)
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                adapter.setCategories(appState)
            }
            is AppState.Error -> {
                binding.categoriesRv.showSnackBarLocal(
                    getString(R.string.error),
                    getString(R.string.reload),
                    { viewModel.getDataFromRemoteSourse(showAdultContent) }
                )
            }
            is AppState.Loading -> {
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

    interface OnItemViewClickListener {
        fun onItemViewClick(film: Film)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_fragment_menu, menu)
        menu.get(0).isChecked = showAdultContent
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_adult_content -> {
                showAdultContent = !item.isChecked
                item.isChecked = showAdultContent
                saveAdultContentKey()
                viewModel.getDataFromRemoteSourse(showAdultContent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveAdultContentKey() {
        activity?.let {
            with(it.getPreferences(Context.MODE_PRIVATE).edit()) {
                putBoolean(ADULT_CONTENT_KEY, showAdultContent)
                apply()
            }
        }
    }
}