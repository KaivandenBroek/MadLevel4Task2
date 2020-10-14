package com.example.madlevel4task2.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.madlevel4task2.model.GameResult
import com.example.madlevel4task2.R
import com.example.madlevel4task2.database.ResultRepository
import com.example.madlevel4task2.enums.Move
import com.example.madlevel4task2.enums.Outcome
import kotlinx.android.synthetic.main.fragment_game.*
import kotlinx.coroutines.*
import java.util.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class GameFragment : Fragment() {
    private val mainScope = CoroutineScope(Dispatchers.Main)
    private lateinit var resultRepository: ResultRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        activity?.setTitle(R.string.app_name) // when returned, the app name didnt change back
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        resultRepository = ResultRepository(requireContext())
        setHasOptionsMenu(true)

        initViews()
    }

    private fun initViews() {
        stats()
        ib_rock.setOnClickListener { gameStart(Move.ROCK) }
        ib_paper.setOnClickListener { gameStart(Move.PAPER) }
        ib_scissors.setOnClickListener { gameStart(Move.SCISSORS) }
    }

    /**
     * Whenever a rock, paper or scissors button is clicked, a new game will start
     * the clicked button with a value will be compared with the random value and
     * thus will a winner be chosen, also the outcome is stored
     */
    private fun gameStart(handUser: Move) {
        val outcome: Outcome?

        val handPC = (0..2).random().toEnum<Move>()

        showGame(handUser, handPC)

        outcome = matchCheck(handUser, handPC)

        addMatch(handUser, handPC, outcome)

        stats()
    }

    private fun showGame(handUser: Move, handPC: Move) {
        when (handUser) {
            Move.ROCK -> iv_you.setImageResource(R.drawable.rock)
            Move.PAPER -> iv_you.setImageResource(R.drawable.paper)
            Move.SCISSORS -> iv_you.setImageResource(R.drawable.scissors)
        }
        when (handPC) {
            Move.ROCK -> iv_computer.setImageResource(R.drawable.rock)
            Move.PAPER -> iv_computer.setImageResource(R.drawable.paper)
            Move.SCISSORS -> iv_computer.setImageResource(R.drawable.scissors)
        }
    }

    /*
    adds match data in match object to database
     */
    private fun addMatch(handUser: Move, handPC: Move, outcome: Outcome) {
        mainScope.launch {
            val result = GameResult(
                date = Calendar.getInstance().time,
                handUser = handUser,
                handPC = handPC,
                outcome = outcome
            )

            withContext(Dispatchers.IO) {
                resultRepository.insertResult(result)
                Log.i("database", "added game to database: $result")
            }
        }
    }

    /*
    Checks match outcomes and compares them
     */
    private fun matchCheck(handUser: Move, handPC: Move): Outcome {
        if (handPC == handUser) return Outcome.DRAW

        return if (handUser == Move.SCISSORS && handPC == Move.ROCK) {
            Outcome.LOSE
        } else if (handUser == Move.ROCK && handPC == Move.PAPER) {
            Outcome.LOSE
        } else if (handUser == Move.PAPER && handPC == Move.SCISSORS) {
            Outcome.LOSE
        } else {
            Outcome.WIN
        }
    }

    /*
    Updates game statistics, and puts it in a string
     */
    private fun stats() {
        mainScope.launch {
            val wins = withContext(Dispatchers.IO) {
                resultRepository.getWins()
            }
            val loses = withContext(Dispatchers.IO) {
                resultRepository.getLoses()
            }
            val draws = withContext(Dispatchers.IO) {
                resultRepository.getDraws()
            }
            tv_score.text = getString(R.string.stat_numbers, wins, draws, loses)
            //tv_score.text = String.format("Win: %d Draw: %d Lose: %d", wins, draws, loses)
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

    // for the converter
    private inline fun <reified Move : Enum<Move>> Int.toEnum(): Move = enumValues<Move>()[this]

}