package com.example.madlevel4task2

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_game.*
import kotlin.random.Random

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class GameFragment : Fragment() {

    private val rock: String = "ROCK"
    private val paper: String = "PAPER"
    private val scissors: String = "SCISSORS"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        ib_rock.setOnClickListener {
            gameStart(rock)
        }
        ib_paper.setOnClickListener {
            gameStart(paper)
        }
        ib_scissors.setOnClickListener {
            gameStart(scissors)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_history -> {
                findNavController().navigate(R.id.fragment_history)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * Whenever a rock, paper or scissors button is clicked, a new game will start
     * the clicked button with a value will be compared with the random value and
     * thus will a winner be chosen
     */
    private fun gameStart(handUser: String) {
        var handPC: String = ""

        //change your hand to button of hand picked
        when (handUser) {
            rock -> iv_you.setImageResource(R.drawable.rock)
            paper -> iv_you.setImageResource(R.drawable.paper)
            scissors -> iv_you.setImageResource(R.drawable.scissors)
        }

        // i wanted to get this in a separate funtion,
        // but i didn't know what to put as default return value
        when (Random.nextInt(1, 4)) {
            1 -> {
                handPC = rock
                iv_computer.setImageResource(R.drawable.rock)
            }
            2 -> {
                handPC = paper
                iv_computer.setImageResource(R.drawable.paper)
            }
            3 -> {
                handPC = scissors
                iv_computer.setImageResource(R.drawable.scissors)
            }
        }

        if (handPC == handUser) {
            //return 0
        }

        tv_winner.text = String.format("WINNER")

        tv_score.text = String.format("Win: Draw: Lose:")

    }
}