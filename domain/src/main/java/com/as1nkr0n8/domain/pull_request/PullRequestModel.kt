package com.as1nkr0n8.domain.pull_request

import java.time.OffsetDateTime

data class PullRequestModel(
    val prNumber: Int,
    val title: String, //required
    val description: String,
    val state: PullRequestState,
    val createdDate: OffsetDateTime, //required
    val closedDate: OffsetDateTime, //required
    val userName: String, //required
    val userImageUrl: String //required
)

enum class PullRequestState { OPEN, CLOSED }
