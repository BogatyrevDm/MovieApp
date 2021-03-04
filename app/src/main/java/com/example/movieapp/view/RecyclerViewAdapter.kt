package com.example.movieapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.model.Film

class RecyclerViewAdapter(private var onItemViewClickListener: MainFragment.OnItemViewClickListener?) :
    RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {
    private var dataSource: List<Film> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        return MyViewHolder(v)
    }

    fun removeListener() {
        onItemViewClickListener = null
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
            var titleTV = itemView.findViewById<TextView>(R.id.title)
            titleTV.text = film.title

            var averageVoteTV = itemView.findViewById<TextView>(R.id.average_vote)
            averageVoteTV.text = film.averageVote.toString()

            var releasDateTV = itemView.findViewById<TextView>(R.id.releas_date)
            releasDateTV.text = film.releaseDate

            var iconIV = itemView.findViewById<AppCompatImageView>(R.id.poster)
            iconIV.setImageResource(R.drawable.screen)

            itemView.findViewById<CardView>(R.id.card_view_root).setOnClickListener({
                onItemViewClickListener?.onItemViewClick(film)
            }

            )
        }
    }
}