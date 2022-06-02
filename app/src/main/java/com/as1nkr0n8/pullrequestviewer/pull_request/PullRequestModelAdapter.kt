package com.as1nkr0n8.pullrequestviewer.pull_request

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.as1nkr0n8.domain.pull_request.PullRequestModel
import com.as1nkr0n8.pullrequestviewer.R
import com.as1nkr0n8.pullrequestviewer.databinding.PullRequestCardBinding
import com.bumptech.glide.Glide

class PullRequestModelAdapter(private val pullRequestList: MutableList<PullRequestModel>) :
    RecyclerView.Adapter<PullRequestModelAdapter.PRViewHolder>() {

    fun updateList(list: List<PullRequestModel>) {
        pullRequestList.clear()
        pullRequestList.addAll(list.sortedBy { it.prNumber })
        notifyDataSetChanged()
    }

    class PRViewHolder(binding: PullRequestCardBinding) : RecyclerView.ViewHolder(binding.root) {
        private val prCard = binding.prCard
        private val prNumber = binding.prNumber
        private val title = binding.title
        private val description = binding.description
        private val createdDate = binding.createdDate
        private val closedDate = binding.closedDate
        private val userName = binding.userName
        internal val userImageView = binding.userImage

        fun bindModel(prModel: PullRequestModel) {
            prCard.setOnClickListener {
                if (description.isVisible) {
                    description.visibility = View.GONE
                } else {
                    description.visibility = View.VISIBLE
                }
            }
            prNumber.text = prModel.prNumber.toString()
            title.text = prModel.title
            description.text = prModel.description
            createdDate.text = prModel.createdDate
            closedDate.text = prModel.closedDate
            userName.text = prModel.userName
            if (prModel.userImageUrl.isEmpty()) {
                userImageView.setImageResource(R.drawable.ic_launcher_foreground)
            } else {
                Glide.with(userImageView.context)
                    .load(prModel.userImageUrl)
                    .centerCrop()
                    .into(userImageView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PRViewHolder {
        val binding =
            PullRequestCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PRViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PRViewHolder, position: Int) {
        holder.bindModel(pullRequestList[position])
    }

    override fun getItemCount(): Int {
        return pullRequestList.size
    }

    override fun onViewRecycled(holder: PRViewHolder) {
        super.onViewRecycled(holder)
        Glide.with(holder.userImageView.context).clear(holder.userImageView)
    }
}
