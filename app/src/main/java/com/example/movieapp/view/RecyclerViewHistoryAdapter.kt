package com.example.movieapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.model.Film
import com.example.movieapp.model.FilmSummary

class RecyclerViewHistoryAdapter :
    RecyclerView.Adapter<RecyclerViewHistoryAdapter.HistoryViewHolder>() {
    private var dataSource = listOf<FilmSummary>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val v: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_view_history, parent, false)
        return HistoryViewHolder(v)
    }

    fun setData(data: List<FilmSummary>) {
        dataSource = data
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.onBind(dataSource[position])
    }

    override fun getItemCount(): Int {
        return dataSource.count()
    }

    inner class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(filmSummary: FilmSummary) {
            itemView.findViewById<TextView>(R.id.history_rv_item).text =
                String.format(
                    "%s %s %.1f",
                    filmSummary.title,
                    filmSummary.releaseDate,
                    filmSummary.averageVote

                )
        }
    }


}