package com.example.swantest.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.swantest.R
import com.example.swantest.common.BindableViewHolder
import com.example.swantest.common.extension.inflate
import com.example.swantest.databinding.ListItemPullRequestBinding
import com.example.swantest.model.PullRequestModel
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_pull_request.view.*
import java.lang.Exception

class PullRequestAdapter : RecyclerView.Adapter<PullRequestAdapter.PullRequestHolder>() {
    private var pullRequestList = ArrayList<PullRequestModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PullRequestHolder {
        val view = parent.inflate(R.layout.list_item_pull_request)
        return PullRequestHolder(view)
    }

    override fun onBindViewHolder(holder: PullRequestHolder, position: Int) {
        val model = pullRequestList[position]
        holder.bind(model)
        if (model.user?.avatar_url?.isNotEmpty()!!) {
            Picasso.get().load(model.user?.avatar_url)
                .into(holder.itemView.imageViewProfile, object : Callback {
                    override fun onSuccess() {

                    }

                    override fun onError(e: Exception?) {
                        holder.itemView.imageViewProfile.setImageResource(R.drawable.ic_profile_placeholder)
                    }
                })
        } else {
            holder.itemView.imageViewProfile.setImageResource(R.drawable.ic_profile_placeholder)
        }
    }

    override fun getItemCount(): Int = pullRequestList.size

    fun updateList(pullRequestList: ArrayList<PullRequestModel>) {
        this.pullRequestList = pullRequestList
        notifyDataSetChanged()
    }

    inner class PullRequestHolder(view: View) :
        BindableViewHolder<ListItemPullRequestBinding, PullRequestModel>(view) {
        override fun bind(viewModel: PullRequestModel) {
            binding?.model = viewModel
        }
    }
}