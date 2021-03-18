package com.example.movieapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.model.Categories
import com.example.movieapp.model.Film

class RecyclerViewCategoriesAdapter(private var onItemViewClickListener: MainFragment.OnItemViewClickListener?) :
    RecyclerView.Adapter<RecyclerViewCategoriesAdapter.MyViewHolder>() {
    private var dataSource: MutableMap<Categories, List<Film>> = mutableMapOf(
        Categories.NOWPLAYING to listOf(),
        Categories.POPULAR to listOf(),
        Categories.TOPRATED to listOf(),
        Categories.UPCOMING to listOf(),
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view_categories, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val category = Categories.values()[position]
        holder.onBind(getResIdByCategory(category), dataSource[category])
    }

    private fun getResIdByCategory(category: Categories): Int {
        return when (category) {
            Categories.NOWPLAYING -> R.string.now_playing
            Categories.POPULAR -> R.string.popular
            Categories.TOPRATED -> R.string.top_rated
            Categories.UPCOMING -> R.string.upcoming
        }
    }

    override fun getItemCount(): Int {
        return dataSource.size
    }

    fun setCategories(filmList: List<Film>, position: Int) {
        val category = Categories.values()[position]
        dataSource[category] = filmList
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(resId: Int, films: List<Film>?) {

            itemView.apply {
                findViewById<TextView>(R.id.category_title).text =
                    itemView.resources.getString(resId)
            }

            itemView.findViewById<RecyclerView>(R.id.category_rv).apply {
                val adapter = RecyclerViewFilmsAdapter(onItemViewClickListener)
                films?.let { adapter.setFilms(films) }

                this.adapter = adapter
                this.layoutManager =
                    LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            }
        }
    }
}