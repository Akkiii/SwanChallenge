package com.example.swantest.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.swantest.R
import com.example.swantest.adapter.PullRequestAdapter
import com.example.swantest.common.extension.isInternetAvailable
import com.example.swantest.common.extension.observe
import com.example.swantest.database.PullRequestDatabase
import com.example.swantest.model.PullRequestModel
import com.example.swantest.viewmodel.PullRequestViewModel
import kotlinx.android.synthetic.main.fragment_recyclerview.*

class ClosePRFragment : Fragment() {
    private val viewModel by lazy {
        ViewModelProvider(this).get(PullRequestViewModel::class.java)
    }

    private var closedPRList = ArrayList<PullRequestModel>()
    private var closedPRAdapter: PullRequestAdapter? = null
    private var pullRequestDB: PullRequestDatabase? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_recyclerview, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureRecyclerView()

        bindViewModel()

        /*Check whether data is available in local DB or not. if its available then it will load from DB otherwise calling Webservice for Retrive new data*/
        pullRequestDB = PullRequestDatabase.getDatabase(requireContext())
        if (pullRequestDB?.pullRequestDao() != null) {
            if (pullRequestDB?.pullRequestDao()?.getOpenPullRequest("closed")?.size!! > 0) {
                val openList = pullRequestDB?.pullRequestDao()?.getOpenPullRequest("closed")
                progressBar.visibility = View.GONE
                closedPRList.addAll(openList!!)
                closedPRAdapter?.updateList(closedPRList)
            } else {
                if (requireContext().isInternetAvailable())
                    viewModel.onEventReceived(PullRequestViewModel.Event.GetPullRequestData("closed"))
            }
        } else {
            if (requireContext().isInternetAvailable())
                viewModel.onEventReceived(PullRequestViewModel.Event.GetPullRequestData("closed"))
        }
    }

    private fun configureRecyclerView() {
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        closedPRAdapter = PullRequestAdapter()
        recyclerView.adapter = closedPRAdapter
    }

    private fun bindViewModel() {
        observe(viewModel.state)
        {
            when (it) {
                is PullRequestViewModel.State.GetClosedPRSuccessData -> {
                    progressBar.visibility = View.GONE
                    closedPRList.addAll(it.data)
                    pullRequestDB?.pullRequestDao()?.insertPullRequest(closedPRList)
                    closedPRAdapter?.updateList(closedPRList)
                }

                is PullRequestViewModel.State.GetClosedPRErrorData -> {
                    progressBar.visibility = View.GONE
                    textViewErrorMessage.visibility = View.VISIBLE
                    textViewErrorMessage.text = it.message
                }
            }
        }
    }
}