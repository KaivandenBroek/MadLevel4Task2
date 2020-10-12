package com.example.madlevel4task2.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madlevel4task2.model.GameResult
import com.example.madlevel4task2.adapter.HistoryResultAdapter
import com.example.madlevel4task2.R
import com.example.madlevel4task2.database.ResultRepository
import kotlinx.android.synthetic.main.fragment_history.*
import kotlinx.coroutines.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class HistoryFragment : Fragment() {

    private lateinit var resultRepository: ResultRepository
    private var results = arrayListOf<GameResult>()
    private val resultAdapter = HistoryResultAdapter(results)
    private val mainScope = CoroutineScope(Dispatchers.Main)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        activity?.setTitle(R.string.history_title)
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        resultRepository = ResultRepository(requireContext())

        initRv()
    }

    private fun initRv() {
        rv_history.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        rv_history.adapter = resultAdapter
        rv_history.addItemDecoration(
            DividerItemDecoration(
                activity,
                DividerItemDecoration.VERTICAL
            )
        )
        observeAddGameResult()
    }

    private fun deleteHistory() {
        mainScope.launch {
            withContext(Dispatchers.IO) {
                resultRepository.deleteAllResults()
            }
            observeAddGameResult()
        }
    }

    private fun observeAddGameResult() {
        mainScope.launch {
            val history = withContext(Dispatchers.IO) {
                resultRepository.getAllResults()
            }
            results.clear()
            results.addAll(history)
            resultAdapter.notifyDataSetChanged()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_history, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.back_history -> {
                findNavController().navigate(R.id.fragment_game)
                return true
            }
            R.id.action_history -> {
                deleteHistory()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}