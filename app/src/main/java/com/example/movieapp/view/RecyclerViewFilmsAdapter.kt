package com.example.movieapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.movieapp.R
import com.example.movieapp.model.Film

class RecyclerViewFilmsAdapter(private var onItemViewClickListener: MainFragment.OnItemViewClickListener?) :
    RecyclerView.Adapter<RecyclerViewFilmsAdapter.MyViewHolder>() {
    private var dataSource: List<Film> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_view_films, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.onBind(dataSource.get(position))
    }

    override fun getItemCount(): Int {
        return dataSource.size
    }

    fun setFilms(films: List<Film>) {
        dataSource = films
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(film: Film) {

            itemView.apply {
                findViewById<TextView>(R.id.title).text = film.filmSummary.title
            }

            itemView.apply {
                findViewById<TextView>(R.id.average_vote).text = film.filmSummary.averageVote.toString()
            }
            itemView.apply {
                findViewById<TextView>(R.id.releas_date).text = film.filmSummary.releaseDate
            }

            itemView.apply {
                findViewById<AppCompatImageView>(R.id.poster).load("https://image.tmdb.org/t/p/original/${film.filmSummary.posterPath}")
            }
            itemView.apply {
                findViewById<CardView>(R.id.item_view_root).setOnClickListener{
                    onItemViewClickListener?.onItemViewClick(film)
                }
            }
        }
    }
}