package com.as1nkr0n8.pullrequestviewer.pull_request

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.as1nkr0n8.domain.pull_request.PullRequestModel
import com.as1nkr0n8.pullrequestviewer.R
import com.as1nkr0n8.pullrequestviewer.databinding.PullRequestCardBinding

class PullRequestModelAdapter(private val pullRequestList: MutableList<PullRequestModel>) :
    RecyclerView.Adapter<PullRequestModelAdapter.ViewHolder>() {

    fun updateList(list: List<PullRequestModel>) {
        pullRequestList.clear()
        pullRequestList.addAll(list)
        notifyDataSetChanged()
    }

    class ViewHolder(binding: PullRequestCardBinding) : RecyclerView.ViewHolder(binding.root) {
        private val prNumber = binding.prNumber
        private val title = binding.title
        private val description = binding.description
        private val createdDate = binding.createdDate
        private val closedDate = binding.closedDate
        private val userName = binding.userName
        private val userImageUrl = binding.userImage

        fun bindModel(prModel: PullRequestModel) {
            prNumber.text = prModel.prNumber.toString()
            title.text = prModel.title
            if (prModel.description.isEmpty()) {
                description.visibility = View.INVISIBLE
            } else {
                description.text = prModel.description
            }
            createdDate.text = prModel.createdDate
            closedDate.text = prModel.closedDate
            userName.text = prModel.userName
            if (prModel.userImageUrl.isEmpty()) {
                userImageUrl.setImageResource(R.drawable.ic_launcher_foreground)
            } else {
                //userImageUrl.setImageBitmap()
                //TODO("Set image")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            PullRequestCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindModel(pullRequestList[position])
    }

    override fun getItemCount(): Int {
        return pullRequestList.size
    }
}