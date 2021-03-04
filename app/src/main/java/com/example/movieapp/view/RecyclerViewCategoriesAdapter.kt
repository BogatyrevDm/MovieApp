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
    private var dataSource: Map<Categories, List<Film>> = mapOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.item_view_categories, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val category = Categories.values()[position]
        holder.onBind(category.toString(), dataSource[category])
    }

    override fun getItemCount(): Int {
        return dataSource.size
    }

    fun setCategories(categoriesMap: Map<Categories, List<Film>>) {
        dataSource = categoriesMap
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(title:String,films: List<Film>?) {

            var titleTV = itemView.findViewById<TextView>(R.id.category_title)
            titleTV.text = title

            val recyclerView = itemView.findViewById<RecyclerView>(R.id.category_rv)
            val adapter = RecyclerViewFilmsAdapter(onItemViewClickListener)
            if (films != null) {
                adapter.setFilms(films)
            }
            recyclerView.adapter = adapter
            recyclerView.layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)

//            var titleTV = itemView.findViewById<TextView>(R.id.title)
//            titleTV.text = film.title
//
//            var averageVoteTV = itemView.findViewById<TextView>(R.id.average_vote)
//            averageVoteTV.text = film.averageVote.toString()
//
//            var releasDateTV = itemView.findViewById<TextView>(R.id.releas_date)
//            releasDateTV.text = film.releaseDate
//
//            var iconIV = itemView.findViewById<AppCompatImageView>(R.id.poster)
//            iconIV.setImageResource(R.drawable.screen)
//
//            itemView.findViewById<CardView>(R.id.card_view_root).setOnClickListener({
//                onItemViewClickListener?.onItemViewClick(film)
//            }

            //            )
        }
    }
}