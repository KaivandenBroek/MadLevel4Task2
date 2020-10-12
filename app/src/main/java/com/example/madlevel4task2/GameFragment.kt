package com.example.madlevel4task2

import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.example.madlevel4task2.enums.Outcome
import kotlinx.android.synthetic.main.fragment_game.*
import kotlinx.coroutines.*
import java.util.*
import kotlin.random.Random

const val REQ_RESULT_KEY = "req_result"
const val BUNDLE_RESULT_KEY = "bundle_result"

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class GameFragment : Fragment() {

    private val rock: String = "ROCK"
    private val paper: String = "PAPER"
    private val scissors: String = "SCISSORS"

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

        stats()
        // every button calls the same function, but with a different input
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

    /**
     * Whenever a rock, paper or scissors button is clicked, a new game will start
     * the clicked button with a value will be compared with the random value and
     * thus will a winner be chosen, also the outcome is stored
     */
    private fun gameStart(handUser: String) {
        var handPC: String = ""
        var outcome: Outcome?

        //change your hand to button of hand picked
        when (handUser) {
            rock -> iv_you.setImageResource(R.drawable.rock)
            paper -> iv_you.setImageResource(R.drawable.paper)
            scissors -> iv_you.setImageResource(R.drawable.scissors)
        }

        // i wanted to get this in a separate function,
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

        outcome = matchCheck(handPC, handUser)
        // update score
        addMatch(handUser, handPC, outcome)
        stats()
    }

    private fun addMatch(handUser: String, handPC: String, outcome: Outcome) {
        mainScope.launch {
            val result = GameResult (
                date = Calendar.getInstance().time,
                handUser = handUser,
                handPC = handPC,
                outcome = outcome
            )

            withContext(Dispatchers.IO) {
                resultRepository.insertResult(result)
            }
            resultRepository.getAllResults()
            setFragmentResult(REQ_RESULT_KEY,
            bundleOf(Pair(BUNDLE_RESULT_KEY, result)))
        }

    }

    private fun matchCheck(handPC: String, handUser: String): Outcome {
        if (handPC == handUser) return Outcome.DRAW

        return if (handPC == rock && handUser == paper || handPC == paper && handUser == scissors ||
            handPC == scissors && handUser == rock) {
            Outcome.WIN
        } else {
            Outcome.LOSE
        }

    }

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
            tv_score.text = String.format("Win: %d Draw: %d Lose: %d", wins, draws, loses)
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

}