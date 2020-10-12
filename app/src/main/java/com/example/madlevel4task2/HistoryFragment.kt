package com.example.madlevel4task2

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_history.*
import kotlinx.coroutines.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class HistoryFragment : Fragment() {

    private var results: ArrayList<GameResult> = arrayListOf()
    private lateinit var resultRepository: ResultRepository
    private val resultAdapter = ResultAdapter(results)
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

        initRv()
        observeAddGameResult()
        resultRepository = ResultRepository(requireContext())
        getHistoryFromDatabase()
    }

    private fun  initRv() {
        rv_history.adapter = resultAdapter
        rv_history.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        rv_history.addItemDecoration(DividerItemDecoration(activity,
                DividerItemDecoration.VERTICAL
            )
        )
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

    private fun getHistoryFromDatabase() {
        mainScope.launch {
            val newResults = withContext(Dispatchers.IO) {
                resultRepository.getAllResults()
            }
            this@HistoryFragment.results.clear()
            this@HistoryFragment.results.addAll(newResults)
            this@HistoryFragment.resultAdapter.notifyDataSetChanged()
        }

    }

    private fun deleteHistory() {
        mainScope.launch {
            withContext(Dispatchers.IO) {
                resultRepository.deleteAllResults()
            }
            getHistoryFromDatabase()
        }
    }

    private fun observeAddGameResult() {
        setFragmentResultListener(REQ_RESULT_KEY) { key, bundle ->
            bundle.getParcelable<GameResult>(BUNDLE_RESULT_KEY)?.let {
                val result = GameResult(
                    date = it.date,
                    handUser = it.handUser,
                    handPC = it.handPC,
                    outcome = it.outcome)
                    print(result)
                results.add(result)
                resultAdapter.notifyDataSetChanged()
            }
        }
    }
}