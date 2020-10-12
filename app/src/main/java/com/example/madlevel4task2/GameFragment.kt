package com.example.madlevel4task2

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
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
    private var win: Int = 0
    private var lose: Int = 0
    private var draw: Int = 0

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
     * thus will a winner be chosen, also the outcome is stored
     */
    private fun gameStart(handUser: String) {
        var handPC: String = ""
        var outcome: String = ""

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

        //draw
        if (handPC == handUser) {
            tv_winner.text = String.format("Draw")
            outcome = "Draw"
            draw++
            //TODO stats in database
        }

        //win
        if (handPC == rock && handUser == paper || handPC == paper && handUser == scissors ||
            handPC == scissors && handUser == rock
        ) {
            tv_winner.text = String.format("You win!")
            outcome = "You win!"
            win++
        }

        //lose
        if (handPC == rock && handUser == scissors || handPC == paper && handUser == rock ||
            handPC == scissors && handUser == paper
        ) {
            tv_winner.text = String.format("Computer wins!")
            outcome = "Computer wins!"
            lose++
        }

        // update score
        tv_score.text = String.format("Win: %d Draw: %d Lose: %d", win, draw, lose)
        addMatch(handUser, handPC, outcome)
    }

    private fun addMatch(handUser: String, handPC: String, outcome: String) {
        val date = Calendar.getInstance()


        mainScope.launch {
            val result = GameResult (
                date = date.toString(),
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
}