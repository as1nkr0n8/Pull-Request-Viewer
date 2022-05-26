package com.as1nkr0n8.domain.pull_request

data class PullRequestModel(
    val prNumber: Int,
    val title: String, //required
    val description: String,
    val state: PullRequestState,
    val createdDate: String, //required
    val closedDate: String, //required
    val userName: String, //required
    val userImageUrl: String //required
)

enum class PullRequestState { OPEN, CLOSED }
