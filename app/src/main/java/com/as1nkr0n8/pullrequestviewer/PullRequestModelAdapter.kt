package com.as1nkr0n8.pullrequestviewer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.as1nkr0n8.domain.pull_request.PullRequestModel
import com.as1nkr0n8.pullrequestviewer.databinding.PullRequestCardBinding
import java.time.format.DateTimeFormatter

class PullRequestModelAdapter(private val pullRequestList: MutableList<PullRequestModel>) : RecyclerView.Adapter<PullRequestModelAdapter.ViewHolder>() {

    fun updateList(list: List<PullRequestModel>) {
        pullRequestList.clear()
        pullRequestList.addAll(list)
        notifyDataSetChanged()
    }

    class ViewHolder(binding: PullRequestCardBinding) : RecyclerView.ViewHolder(binding.root) {
        private val title = binding.title
        private val description = binding.description
        private val createdDate = binding.createdDate
        private val closedDate = binding.closedDate
        private val userName = binding.userName
        private val userImageUrl = binding.userImage

        fun bindModel(prModel: PullRequestModel) {
            title.text = prModel.title
            description.text = prModel.description
            createdDate.text = prModel.createdDate.format(DateTimeFormatter.BASIC_ISO_DATE)
            closedDate.text = prModel.closedDate.format(DateTimeFormatter.BASIC_ISO_DATE)
            userName.text = prModel.userName
            if(prModel.userImageUrl.isEmpty()) {
                userImageUrl.setImageResource(R.drawable.ic_launcher_foreground)
            } else {
                //userImageUrl.setImageBitmap()
                TODO("Set image")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = PullRequestCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindModel(pullRequestList[position])
    }

    override fun getItemCount(): Int {
        return pullRequestList.size
    }
}