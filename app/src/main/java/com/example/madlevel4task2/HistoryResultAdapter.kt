package com.example.madlevel4task2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.madlevel4task2.enums.Outcome
import kotlinx.android.synthetic.main.history_item.view.*

class HistoryResultAdapter(private val results: List<GameResult>) :
    RecyclerView.Adapter<HistoryResultAdapter.ViewHolder>(){

     class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(gameResult: GameResult) {
            //itemView.tv_winner_history.text = gameResult.outcome.toString()
            itemView.tv_date.text = gameResult.date.toString()
            when(gameResult.outcome) {
                Outcome.WIN -> {
                    itemView.tv_winner_history.text = itemView.resources.getString(R.string.win)
                }
                Outcome.LOSE -> {
                    itemView.tv_winner_history.text = itemView.resources.getString(R.string.win)
                }
                Outcome.DRAW -> {
                    itemView.tv_winner_history.text = itemView.resources.getString(R.string.win)
                }
            }

            when(gameResult.handPC) {
                "ROCK"-> itemView.iv_computer_history.setImageResource(R.drawable.rock)
                "PAPER"-> itemView.iv_computer_history.setImageResource(R.drawable.paper)
                "SCISSORS"-> itemView.iv_computer_history.setImageResource(R.drawable.scissors)
            }
            when(gameResult.handUser) {
                "ROCK"-> itemView.iv_user_history.setImageResource(R.drawable.rock)
                "PAPER"-> itemView.iv_user_history.setImageResource(R.drawable.paper)
                "SCISSORS"-> itemView.iv_user_history.setImageResource(R.drawable.scissors)
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
        holder.bind(results[position])
    }
}