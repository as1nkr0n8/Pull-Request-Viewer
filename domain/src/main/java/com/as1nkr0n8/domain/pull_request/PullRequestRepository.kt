package com.as1nkr0n8.domain.pull_request

import com.as1nkr0n8.domain.common.Error
import com.as1nkr0n8.domain.common.Result

interface PullRequestRepository {
    suspend fun fetchAllPullRequests(): Result<List<PullRequestModel>, Error>
    suspend fun fetchPullRequestByPRNumber(prNumber: String): Result<PullRequestModel?, Error>
    suspend fun fetchPullRequestsByState(state: PullRequestState): Result<List<PullRequestModel>, Error>
}