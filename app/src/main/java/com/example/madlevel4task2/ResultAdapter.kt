package com.example.madlevel4task2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.history_item.view.*

class ResultAdapter(private val results: List<GameResult>) :
RecyclerView.Adapter<ResultAdapter.ViewHolder>(){

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun databind(gameResult: GameResult) {
            itemView.tv_winner_history.text = gameResult.outcome
            itemView.tv_date.text = gameResult.date
            when(gameResult.handPC) {
                "ROCK"-> itemView.iv_computer.setImageResource(R.drawable.rock)
                "PAPER"-> itemView.iv_computer.setImageResource(R.drawable.paper)
                "SCISSORS"-> itemView.iv_computer.setImageResource(R.drawable.scissors)
            }
            when(gameResult.handUser) {
                "ROCK"-> itemView.iv_user.setImageResource(R.drawable.rock)
                "PAPER"-> itemView.iv_user.setImageResource(R.drawable.paper)
                "SCISSORS"-> itemView.iv_user.setImageResource(R.drawable.scissors)
            }
        }
    }

    override fun getItemCount(): Int {
        return results.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.history_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.databind(results[position])
    }
}