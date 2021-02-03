package com.example.swantest.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.swantest.MainActivity
import com.example.swantest.R
import com.example.swantest.adapter.PullRequestAdapter
import com.example.swantest.common.extension.isInternetAvailable
import com.example.swantest.common.extension.observe
import com.example.swantest.database.PullRequestDatabase
import com.example.swantest.model.PullRequestModel
import com.example.swantest.viewmodel.PullRequestViewModel
import kotlinx.android.synthetic.main.fragment_recyclerview.*

class OpenPRFragment : Fragment() {
    private val viewModel by lazy {
        ViewModelProvider(this).get(PullRequestViewModel::class.java)
    }

    private var openPRList = ArrayList<PullRequestModel>()
    private var openPRAdapter: PullRequestAdapter? = null
    private var pullRequestDB: PullRequestDatabase? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recyclerview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureRecyclerView()

        bindViewModel()

        /*Check whether data is available in local DB or not. if its available then it will load from DB otherwise calling Webservice for Retrive new data*/
        pullRequestDB = PullRequestDatabase.getDatabase(requireContext())
        if (pullRequestDB?.pullRequestDao() != null) {
            if (pullRequestDB?.pullRequestDao()?.getOpenPullRequest("open")?.size!! > 0) {
                val openList = pullRequestDB?.pullRequestDao()?.getOpenPullRequest("open")
                progressBar.visibility = View.GONE
                openPRList.addAll(openList!!)
                openPRAdapter?.updateList(openPRList)
            } else {
                if (requireContext().isInternetAvailable())
                    viewModel.onEventReceived(PullRequestViewModel.Event.GetPullRequestData("open"))
            }
        } else {
            if (requireContext().isInternetAvailable())
                viewModel.onEventReceived(PullRequestViewModel.Event.GetPullRequestData("open"))
        }
    }

    private fun configureRecyclerView() {
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        openPRAdapter = PullRequestAdapter()
        recyclerView.adapter = openPRAdapter
    }

    private fun bindViewModel() {
        observe(viewModel.state)
        {
            when (it) {
                is PullRequestViewModel.State.GetOpenPRSuccessData -> {
                    progressBar.visibility = View.GONE
                    openPRList.addAll(it.data)
                    pullRequestDB?.pullRequestDao()?.insertPullRequest(openPRList)
                    openPRAdapter?.updateList(openPRList)
                }

                is PullRequestViewModel.State.GetOpenPRErrorData -> {
                    progressBar.visibility = View.GONE
                    textViewErrorMessage.visibility = View.VISIBLE
                    textViewErrorMessage.text = it.message
                }
            }
        }
    }
}