package com.as1nkr0n8.pullrequestviewer

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.as1nkr0n8.pullrequestviewer.databinding.ActivityMainBinding
import com.as1nkr0n8.pullrequestviewer.pull_request.PullRequestModelAdapter
import com.as1nkr0n8.pullrequestviewer.pull_request.PullRequestViewModel
import com.as1nkr0n8.pullrequestviewer.pull_request.PullRequestViewModelFactory
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val pullRequestViewModel by viewModels<PullRequestViewModel> {
        PullRequestViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val adapter = PullRequestModelAdapter(mutableListOf())
        val recyclerView: RecyclerView = findViewById(R.id.prRecyclerView)
        recyclerView.apply {
            this.adapter = adapter
            this.layoutManager = LinearLayoutManager(context)
        }

        binding.refreshData.setOnClickListener {
            adapter.clear()
            pullRequestViewModel.getClosedPRs()
        }

        //set observers
        pullRequestViewModel.list.observe(this) { list ->
            adapter.updateList(list)
        }
        pullRequestViewModel.errorEvent.observe(this) { error ->
            val snackBar =
                Snackbar.make(
                    binding.root,
                    "${error.code}: ${error.message}",
                    Snackbar.LENGTH_INDEFINITE
                )
                    .setAnchorView(R.id.refreshData)
            snackBar.setAction("Close") { snackBar.dismiss() }
            snackBar.show()
        }
    }

    override fun onResume() {
        super.onResume()
        pullRequestViewModel.getClosedPRs()
    }
}